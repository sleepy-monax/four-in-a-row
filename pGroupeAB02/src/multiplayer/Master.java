package multiplayer;

import network.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import controller.GameController;
import message.OnGameTick;
import models.*;

public class Master extends GameController implements ConnectionListener {
    private Server server;
    private HashMap<Connection, ConnectedSlave> slaves;

    public Master(Game game, int port) {
        super(game);

        slaves = new HashMap<>();

        server = new Server();
        server.setListener(this);
        server.start(port);

        game.getMessageLoop().registerNotifier(OnGameTick.class, message -> {
            server.broadcast(new PacketBuilder(PacketType.TICK).build());
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

        switch (packet.getPacketType()) {
            case LOGIN:
                System.out.println("Player login" + connection.toString() + "...");

                ConnectedSlave slave = this.slaves.get(connection);

                try {
                    Player newPlayer = game().joinPlayer(reader.readString());

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

                break;
            default:
                System.out.println("Unexpected packet " + packet.toString());
        }
    }

}
