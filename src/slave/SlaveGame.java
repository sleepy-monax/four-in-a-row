package slave;

import network.Connection;
import network.ConnectionListener;
import network.Packet;

import java.io.IOException;

public class SlaveGame implements ConnectionListener {
    private Connection connection;

    public SlaveGame(String address, int port) {
        connection = new Connection();
        connection.setClientListener(this);
        connection.connect(address, port);
    }

    @Override
    public void onConnect(Connection connection) {
        System.out.println("Hello je suis le slave et mon master est " + connection.toString());
    }

    @Override
    public void onDisconnect(Connection connection) {
        System.out.println("Je suis le slave et le master n'est plus mon ami :/");
    }

    @Override
    public void onReceive(Packet p, Connection connection) throws IOException {

    }
}
