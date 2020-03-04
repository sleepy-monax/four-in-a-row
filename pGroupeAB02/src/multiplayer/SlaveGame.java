package multiplayer;

import network.*;

import java.io.IOException;

import message.GameDisconnected;
import models.*;

public class SlaveGame extends Game implements ConnectionListener {
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
        shutdown();
    }

    @Override
    public void onDisconnectByRemote(Connection connection) {
        getMessageLoop().post(new GameDisconnected());
    }

    @Override
    public void onReceive(Packet packet, Connection connection) throws IOException {
        PacketReader reader = new PacketReader(packet);

        switch (packet.getPacketType()) {
            case ACCEPTED:
                localPlayer = reader.readInt();
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
                this.tick();
                break;

            default:
                break;
        }
    }

    @Override
    public void shutdown() {
        connection.close();
    }
}
