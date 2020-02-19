package master;

import javafx.application.Platform;
import models.Deck;
import models.Game;
import models.Player;
import network.Connection;
import network.ConnectionListener;
import network.Packet;
import network.Server;

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
        System.out.println("Je suis le master et slave " + connection.toString() + "vient de ce connecter :)");

        Platform.runLater(() -> {
            Player player = new Player(connection.getSocket().getInetAddress().toString());
            this.slaves.put(connection, new Slave(player));
            this.addPlayer(player);
        });

        System.out.println("ok");
    }

    @Override
    public void onDisconnect(Connection connection) {
        System.out.println("Je suis le master et le slave " + connection.toString() + "vien de ce deconecter :/");

        Slave slave = this.slaves.get(connection);
        this.removePlayer(slave.getPlayer());
        this.slaves.remove(connection);
    }

    @Override
    public void onReceive(Packet packet, Connection connection) throws IOException {

    }
}
