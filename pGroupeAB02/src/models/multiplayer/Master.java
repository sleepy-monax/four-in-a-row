package models.multiplayer;

import models.Game;
import models.Player;
import models.controller.GameController;
import models.message.OnCountDown;
import models.messageloop.Message;
import models.messageloop.Notifiable;
import network.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Master extends GameController implements ConnectionListener {
    private final Server server;
    private final HashMap<Connection, ConnectedSlave> slaves;
    private final String password;

    public Master(Game game, int port, String password) {
        super(game);

        this.password = password;

        slaves = new HashMap<>();

        server = new Server();
        server.setListener(this);
        server.start(port);

        game.getMessageLoop().registerNotifier(OnCountDown.class, message -> {
            server.broadcast(new PacketBuilder(PacketType.TICK).build());
        });

        game.getMessageLoop().registerNotifier(new Notifiable() {

            @Override
            public void handle(Message message) {
                server.broadcast(message.asPacket());
            }

            @Override
            public boolean canConsume() {
                return false;
            }

            @Override
            public boolean canAccept(Message message) {
                return true;
            }
        });
    }

    public void kickPlayer(int i) {
        for (ConnectedSlave slave : slaves.values()) {
            if (slave.getPlayer() != null && slave.getPlayer().getId() == i) {
                game().removePlayer(slave.getPlayer());
            }
        }
    }

    @Override
    public void shutdown() {
        System.out.println("Shutting down server...");
        server.stop();
    }

    @Override
    public void onConnect(Connection connection) {
        System.out.println("Connecting player " + connection.toString() + "...");
        this.slaves.put(connection, new ConnectedSlave());
    }

    @Override
    public void onDisconnect(Connection connection) {
        System.out.println("Disconnecting player " + connection.toString() + "...");

        ConnectedSlave slave = this.slaves.get(connection);

        Player player = slave.getPlayer();

        if (player != null) {
            server.broadcast(new PacketBuilder(PacketType.PLAYER_LEAVE).withInt(player.getId()).build());
            game().removePlayer(slave.getPlayer());
        }

        this.slaves.remove(connection);
    }

    @Override
    public void onDisconnectByRemote(Connection connection) {

    }

    @Override
    public void onReceive(Packet packet, Connection connection) {
        PacketReader reader = new PacketReader(packet);

        if (packet.getPacketType() == PacketType.LOGIN) {
            System.out.println("Player login" + connection.toString() + "...");

            ConnectedSlave slave = this.slaves.get(connection);

            try {
                String userName = reader.readString();
                Long userPassword = reader.readLong();

                if (userPassword != Objects.hash(password)) {
                    System.out.println("LOGIN: Invalide password for " + userName);
                    connection.close();
                    this.slaves.remove(connection);
                    return;
                }

                Player newPlayer = game().joinPlayer(userName);

                if (newPlayer != null) {
                    connection.send(new PacketBuilder(PacketType.ACCEPTED).withInt(newPlayer.getId()).build());

                    slave.setPlayer(newPlayer);

                    PacketBuilder builder = new PacketBuilder(PacketType.PLAYER_JOIN);

                    builder.withInt(game().getPlayers().size());

                    for (Player player : game().getPlayers()) {
                        builder.withInt(player.getId());
                        builder.withString(player.getName());
                    }

                    server.broadcast(builder.build());

                } else {
                    connection.close();
                    this.slaves.remove(connection);
                }
            } catch (IOException e) {
                e.printStackTrace();
                connection.close();
            }
        } else if (packet.getPacketType() == PacketType.PLAYER_SELECT_THEME) {
            try {
                game().selectTheme(reader.readString());
            } catch (IOException e) {
                e.printStackTrace();
                connection.close();
            }
        } else if (packet.getPacketType() == PacketType.PLAYER_SELECT_MISTERY_THEME) {
            game().selectMisteryTheme();
        } else if (packet.getPacketType() == PacketType.PLAYER_ANSWER) {
            try {
                game().answer(reader.readString());
            } catch (IOException e) {
                e.printStackTrace();
                connection.close();
            }
        } else if (packet.getPacketType() == PacketType.PLAYER_PASS) {
            game().pass();
        } else {
            System.out.println("Unexpected packet " + packet.toString());
        }
    }

}
