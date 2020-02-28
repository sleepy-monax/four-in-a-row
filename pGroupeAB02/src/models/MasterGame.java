package models;

import network.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import controls.PlayerRoomControlState;

public class MasterGame extends Game implements ConnectionListener, PendingGame {
    private Server server;
    private HashMap<Connection, ConnectedSlave> slaves;
    private Timer tickTimer;

    public MasterGame(Deck deck, int port) {
        super(deck);

        slaves = new HashMap<>();

        server = new Server();
        server.setListener(this);
        server.start(port);

        tickTimer = new Timer();

        Game game = this;
        tickTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                server.broadcast(new PacketBuilder(PacketType.TICK).build());
                game.tick();
            }
        }, 0, 1000);
    }

    @Override
    public void kickPlayer(int i) {
        for (ConnectedSlave slave : slaves.values()) {
            if (slave.getPlayer() != null && slave.getPlayer().getId() == i) {
                removePlayer(slave.getPlayer());
            }
        }
    }

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
            this.removePlayer(slave.getPlayer());
        }

        this.slaves.remove(connection);

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

                    if (newPlayer != null) {
                        slave.setPlayer(newPlayer);

                        PacketBuilder builder = new PacketBuilder(PacketType.PLAYER_JOIN);

                        builder.withInt(getPlayers().size());

                        for (Player player : getPlayers()) {
                            builder.withInt(player.getId());
                            builder.withString(player.getName());
                        }

                        server.broadcast(builder.build());

                        connection.send(new PacketBuilder(PacketType.ACCEPTED).withInt(newPlayer.getId()).build());
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

    @Override
    public String getPlayerName(int id) {
        if (getPlayer(id) == null) {
            return "";
        }

        return getPlayer(id).getName();
    }

    @Override
    public PlayerRoomControlState getPlayerState(int id) {
        for (ConnectedSlave slave : slaves.values()) {
            if (slave.getPlayer() != null && slave.getPlayer().getId() == id) {
                return PlayerRoomControlState.CONNECTED;
            }
        }

        return PlayerRoomControlState.WAITING_FOR_CONNECTION;
    }
}
