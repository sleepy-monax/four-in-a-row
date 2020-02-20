package slave;

import models.Game;
import network.*;

import java.io.IOException;

public class SlaveGame extends Game implements ConnectionListener {
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
    public void onReceive(Packet p, Connection connection) throws IOException {

    }
}
