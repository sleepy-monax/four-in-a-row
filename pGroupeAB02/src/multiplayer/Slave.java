package multiplayer;

import network.*;

import java.io.IOException;

import controller.GameController;
import message.GameDisconnected;
import models.*;

public class Slave extends GameController implements ConnectionListener {
    private int localPlayer;
    private Connection connection;
    private String username;

    public Slave(Game game, String address, int port) {
        super(game);

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
        game().getMessageLoop().post(new GameDisconnected());
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
                    game().joinPlayer(reader.readInt(), reader.readString());
                }
                break;

            case PLAYER_LEAVE:
                game().removePlayer(reader.readInt());
                break;

            case TICK:
                game().tick();
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
