package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static utils.ThreadManager.launchThread;

public class Server {
    private final List<Connection> connections;
    private ServerSocket server;
    private ConnectionListener serverListener;

    public Server()
    {
        connections = new ArrayList<>();
    }

    public synchronized void setListener(final ConnectionListener serverListener) {
        this.serverListener = serverListener;
    }

    public synchronized boolean start(final int port)
    {
        try {
            server = new ServerSocket(port);
        } catch (final Exception e) {
            System.out.println("Failled to start server " + e);
            return false;
        }

        launchThread(this::connectionAcceptorService);

        return true;
    }

    private void connectionAcceptorService() {
        while (true) {
            // Wait for a connection
            try {
                final Socket socket = server.accept();
                final Connection client = new Connection();

                // Pass events
                client.setClientListener(new ConnectionListener() {
                    @Override
                    public void onConnect(final Connection connection) {
                        synchronized (connections) {
                            connections.add(connection);
                        }
                        if (serverListener != null) serverListener.onConnect(connection);
                    }

                    @Override
                    public void onDisconnect(final Connection c) {
                        synchronized (connections) {
                            connections.remove(c);
                        }
                        if (serverListener != null) serverListener.onDisconnect(c);
                    }

                    @Override
                    public void onReceive(final Packet p, final Connection connection) throws IOException {
                        if (serverListener != null) serverListener.onReceive(p, connection);
                    }
                });

                client.setSocket(socket);
            } catch (final IOException e) {
                stop();
                break;
            }
        }

    }

    public synchronized void stop() {
        synchronized (connections) {
            // Close all client threads
            for (final Connection connection : connections) {
                // Prevent ConcurrentModification by removing the event listener
                connection.setClientListener(null);
                connection.close();
            }
            connections.clear();
        }

        if (server == null) return;

        try {
            server.close();
        } catch (final Exception e) {
        }
    }
}
