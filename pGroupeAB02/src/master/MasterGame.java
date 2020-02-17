package master;

import network.Connection;
import network.ConnectionListener;
import network.Packet;
import network.Server;

import java.io.IOException;

public class MasterGame implements ConnectionListener {
    private Server server;

    public MasterGame(int port) throws IOException {
        server = new Server();
        server.setListener(this);
        server.start(port);
    }

    @Override
    public void onConnect(Connection connection) {
        System.out.println("Je suis le master et slave " + connection.toString() + "vient de ce connecter :)");
    }

    @Override
    public void onDisconnect(Connection connection) {
        System.out.println("Je suis le master et le slave " + connection.toString() + "vien de ce deconecter :/");
    }

    @Override
    public void onReceive(Packet packet, Connection connection) throws IOException {

    }
}
