package models;

import network.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MasterGame extends Game implements ConnectionListener {
    private Server server;
    private HashMap<Connection, ConnectedSlave> slaves;
    private Timer tickTimer;
    private TimerTask tickService;

    public MasterGame(Deck deck, int port) {
        super(deck);

        slaves = new HashMap<>();

        server = new Server();
        server.setListener(this);
        server.start(port);

        tickTimer = new Timer();

        Game game = this;

        tickService = new TimerTask() {
            @Override
            public void run() {
                server.broadcast(new PacketBuilder(PacketType.TICK).build());
                game.tick();
            }
        };

        tickTimer.schedule(tickService, 0, 1000);
    }

    public void kickPlayer(int i) {
        for (ConnectedSlave slave : slaves.values()) {
            if (slave.getPlayer() != null && slave.getPlayer().getId() == i) {
                removePlayer(slave.getPlayer());
            }
        }
    }

    public void shutdown() {
        System.out.println("Shutting down server...");
        tickService.cancel();
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
            this.removePlayer(slave.getPlayer());
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
                    Player newPlayer = this.joinPlayer(reader.readString());
                    connection.send(new PacketBuilder(PacketType.ACCEPTED).withInt(newPlayer.getId()).build());

                    if (newPlayer != null) {
                        slave.setPlayer(newPlayer);

                        PacketBuilder builder = new PacketBuilder(PacketType.PLAYER_JOIN);

                        builder.withInt(getPlayers().size());

                        for (Player player : getPlayers()) {
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
