package models.multiplayer;

import models.Game;
import models.controller.GameController;
import models.message.GameDisconnected;
import models.message.OnPlayerScoreChange;
import models.messageloop.Message;
import models.states.GameState;
import network.*;

import java.io.IOException;
import java.util.Objects;

public class Slave extends GameController implements ConnectionListener {
    private final Connection connection;

    private final String username;
    private final String password;
    private final String address;
    private final int port;

    private int localPlayer;

    public Slave(Game game, String username, String address, int port, String password) {
        super(game);

        connection = new Connection();
        connection.setClientListener(this);

        this.username = username;
        this.password = password;
        this.address = address;
        this.port = port;

        localPlayer = -1;

        game.getMessageLoop().registerNotifier(OnPlayerScoreChange.class, message -> {
            message.player().setScore(message.getScore());
            message.player().setLevel(message.getLevel());
            message.player().setLevelMax(message.getLevelMax());
        });
    }

    public boolean connect() {
        return connection.connect(address, port);
    }

    @Override
    public void onConnect(Connection connection) {
        System.out.println("Logging in...");
        connection.send(
                new PacketBuilder(PacketType.LOGIN)
                        .withString(username)
                        .withLong(Objects.hash(password))
                        .build()
        );
    }

    @Override
    public void onDisconnect(Connection connection) {
        game().shutdown();
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
                game().setLocalPlayer(game().joinPlayer(localPlayer, username));
                break;

            case PLAYER_JOIN:
                int player_count = reader.readInt();
                for (int i = 0; i < player_count; i++) {
                    if (i != localPlayer) {
                        game().joinPlayer(reader.readInt(), reader.readString());
                    }
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

    public GameState getPassiveState() {
        return new GameState() {
            @Override
            public void selectTheme(String theme) {
                connection.send(
                        new PacketBuilder(PacketType.PLAYER_SELECT_THEME)
                                .withString(theme)
                                .build()
                );
            }

            @Override
            public void selectMisteryTheme() {
                connection.send(
                        new PacketBuilder(PacketType.PLAYER_SELECT_MISTERY_THEME)
                                .build()
                );
            }

            @Override
            public void answer(String answer) {
                connection.send(
                        new PacketBuilder(PacketType.PLAYER_ANSWER)
                                .withString(answer)
                                .build()
                );
            }

            @Override
            public void pass() {
                connection.send(
                        new PacketBuilder(PacketType.PLAYER_PASS)
                                .build()
                );
            }
        };
    }
}
