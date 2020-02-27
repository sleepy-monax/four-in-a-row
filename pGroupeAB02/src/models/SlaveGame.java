package models;

import network.*;

import java.io.IOException;

public class SlaveGame extends Game implements ConnectionListener, PendingGame {
    private int localPlayer;
    private Connection connection;
    private String username;

    public SlaveGame(String username, String address, int port) {
        super(null); // lol

        this.username = username;

        connection = new Connection();
        connection.setClientListener(this);
        connection.connect(address, port);
    }

    @Override
    public void onConnect(Connection connection) {
        System.out.println("Logging in...");
        connection.send(new PacketBuilder(PacketType.LOGIN).withString(username).build());
    }

    @Override
    public void onDisconnect(Connection connection) {
        System.out.println("Disconnected!");
    }

    @Override
    public void onReceive(Packet packet, Connection connection) throws IOException {
        PacketReader reader = new PacketReader(packet);

        switch (packet.getPacketType()) {
            case ACCEPTED:
                localPlayer = reader.readInt();
                System.out.println("Server accepted connection!");
                break;

            case PLAYER_JOIN:
                this.joinPlayer(reader.readInt(), reader.readString());
                break;

            case PLAYER_LEAVE:
                this.removePlayer(reader.readInt());

            default:
                break;
        }
    }

    @Override
    public void kickPlayer(int i) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attachListener(PendingGameListener listener) {
        // TODO Auto-generated method stub
        setPlayerListener(listener);

    }

    @Override
    public void shutdown() {
        // TODO Auto-generated method stub
        connection.close();
    }
}
