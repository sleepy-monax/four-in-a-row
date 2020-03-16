/*
 * MIT License
 *
 * Copyright (c) 2017 Pim van den Berg
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package network;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static utils.ThreadManager.launch;

public class Connection {
    private boolean closeByClient = false;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private ConnectionListener clientListener;

    public Connection() {
    }

    public synchronized void setClientListener(final ConnectionListener clientListener) {
        this.clientListener = clientListener;
    }

    public synchronized boolean connect(final String host, final int port) {
        if (socket != null && !socket.isClosed())
            throw new IllegalStateException("Client not closed");
        if (host.isEmpty() || port == -1)
            throw new IllegalStateException("Host and port are not set");

        try {
            setSocket(new Socket(host, port));
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    private void listenerService() {
        while (true) {
            final Packet packet;

            try {
                packet = Packet.fromStream(dataInputStream);
            } catch (final IOException e) {
                if (clientListener != null && !closeByClient)
                    clientListener.onDisconnectByRemote(this);

                close();
                break;
            }

            // Fire event
            if (clientListener != null) {
                try {
                    clientListener.onReceive(packet, this);
                } catch (final Exception ignored) {
                }
            }
        }
    }

    public synchronized boolean send(final Packet packet) {
        if (!isConnected())
            return false;

        try {
            packet.write(dataOutputStream);
            dataOutputStream.flush();
            return true;
        } catch (final IOException e) {
            return false;
        }
    }

    public synchronized void close() {
        closeByClient = true;

        if (socket == null)
            return;
        if (socket.isClosed())
            return;

        try {
            socket.close();
        } catch (final IOException ignored) {
        }

        if (clientListener != null)
            clientListener.onDisconnect(this);
    }

    public synchronized boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public synchronized InetAddress getInetAddress() {
        return socket.getInetAddress();
    }

    public synchronized Socket getSocket() {
        return socket;
    }

    public synchronized void setSocket(final Socket socket) throws IOException {
        if (this.socket != null && !this.socket.isClosed())
            throw new IllegalStateException("Connection not closed");

        this.socket = socket;
        socket.setKeepAlive(false);
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        launch(this::listenerService);

        if (clientListener != null)
            clientListener.onConnect(this);
    }

    @Override
    public synchronized String toString() {
        return socket.toString();
    }
}
