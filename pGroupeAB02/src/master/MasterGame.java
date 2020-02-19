package master;

import javafx.application.Platform;
import models.Deck;
import models.Game;
import models.Player;
import network.*;

import java.io.IOException;
import java.util.HashMap;

public class MasterGame extends Game implements ConnectionListener {
    private Server server;
    private HashMap<Connection, Slave> slaves;

    public MasterGame(Deck deck, int port) throws IOException {
        super(deck);

        slaves = new HashMap<>();

        server = new Server();
        server.setListener(this);
        server.start(port);
    }

    public void shutdown()
    {
        server.stop();
    }

    @Override
    public void onConnect(Connection connection) {
        // We need to run this later because we are not on the javafx thread
        Platform.runLater(() -> {
            System.out.println("Connecting player "+ connection.toString()+ "...");

            this.slaves.put(connection, new Slave());
        });
    }

    @Override
    public void onDisconnect(Connection connection) {
        // We need to run this later because we are not on the javafx thread
        Platform.runLater(() -> {
            System.out.println("Disconecting player "+ connection.toString()+ "...");

            Slave slave = this.slaves.get(connection);
            this.removePlayer(slave.getPlayer());
            this.slaves.remove(connection);
        });
    }

    @Override
    public void onReceive(Packet packet, Connection connection)
    {
        switch (packet.getPacketType())
        {
            case LOGIN:
                System.out.println("Player login"+ connection.toString()+ "...");

                // We need to run this later because we are not on the javafx thread
                Platform.runLater(() -> {
                    Slave slave = this.slaves.get(connection);
                    try {
                        Player player = new Player(new PacketReader(packet).readString());
                        this.addPlayer(player);
                    } catch (IOException e) {
                        e.printStackTrace();
                        connection.close();
                    }
                });
                break;
            default:
                System.out.println("Unexpected packet " + packet.toString());
        }
    }
}
