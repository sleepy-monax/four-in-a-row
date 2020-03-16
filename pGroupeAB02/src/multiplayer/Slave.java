package multiplayer;

import controller.GameController;
import message.GameDisconnected;
import messageloop.Message;
import models.Game;
import network.*;

import java.io.IOException;
import java.util.Objects;

public class Slave extends GameController implements ConnectionListener {
    private final Connection connection;
    private final String username;
    private final String address;
    private final int port;
    private int localPlayer;

    public Slave(Game game, String username, String address, int port) {
        super(game);

        connection = new Connection();
        connection.setClientListener(this);
        this.address = address;
        this.port = port;
        this.username = username;
    }

    public boolean connect() {
        return connection.connect(address, port);
    }

    @Override
    public void onConnect(Connection connection) {
        System.out.println("Logging in...");
        connection.send(new PacketBuilder(PacketType.LOGIN).withString(username).build());
    }

    @Override
    public void onDisconnect(Connection connection) {
        game().finish();
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
                game().enterLobby();
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

            case GAME_MESSAGE:
                game().getMessageLoop().post(Objects.requireNonNull(Message.fromPacket(packet, game())));
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
