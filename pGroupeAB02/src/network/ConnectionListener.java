package network;

import java.io.IOException;

public interface ConnectionListener {
    void onConnect(final Connection connection);

    void onDisconnectByRemote(final Connection connection);

    void onDisconnect(final Connection connection);

    void onReceive(final Packet p, final Connection connection) throws IOException;
}
