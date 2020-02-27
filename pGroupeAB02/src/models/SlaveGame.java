package models;

import network.*;

import java.io.IOException;

import controls.PlayerRoomControlState;

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
                int player_count = reader.readInt();

                for (int i = 0; i < player_count; i++) {
                    this.joinPlayer(reader.readInt(), reader.readString());
                }

                break;

            case PLAYER_LEAVE:
                this.removePlayer(reader.readInt());
                break;
            case TICK:
                System.out.println("server tick");
                this.tick();
                break;

            default:
                break;
        }
    }

    @Override
    public void kickPlayer(int i) {
        // TODO Auto-generated method stub

    }

    @Override
    public void shutdown() {
        // TODO Auto-generated method stub
        connection.close();
    }

    @Override
    public String getPlayerName(int id) {
        if (this.getPlayer(id) == null)
            return "";

        return this.getPlayer(id).getName();
    }

    @Override
    public PlayerRoomControlState getPlayerState(int id) {
        if (this.getPlayer(id) == null)
            return PlayerRoomControlState.WAITING_FOR_CONNECTION;

        return PlayerRoomControlState.CONNECTED;
    }
}
