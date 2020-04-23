package network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PacketReader {
    private final Packet packet;
    private final DataInputStream dataInputStream;

    public PacketReader(final Packet packet) {
        this.packet = packet;
        dataInputStream = new DataInputStream(new ByteArrayInputStream(packet.getData()));
    }

    public short getPacketID() {
        return packet.getPacketID();
    }

    public synchronized byte readByte() throws IOException {
        return dataInputStream.readByte();
    }

    public synchronized byte[] readBytes() throws IOException {
        final int dataLength = dataInputStream.readInt();

        final byte[] data = new byte[dataLength];
        if (dataLength == 0)
        {
            return data;
        }

        final int dataRead = dataInputStream.read(data, 0, dataLength);
        if (dataRead != dataLength)
            throw new IOException("Not enough data available");

        return data;
    }

    public synchronized int readInt() throws IOException {
        return dataInputStream.readInt();
    }

    public synchronized String readString() throws IOException {
        return new String(readBytes(), StandardCharsets.UTF_8);
    }

    public synchronized boolean readBoolean() throws IOException {
        return dataInputStream.readBoolean();
    }

    public synchronized float readFloat() throws IOException {
        return dataInputStream.readFloat();
    }

    public synchronized double readDouble() throws IOException {
        return dataInputStream.readDouble();
    }

    public synchronized long readLong() throws IOException {
        return dataInputStream.readLong();
    }

    public synchronized short readShort() throws IOException {
        return dataInputStream.readShort();
    }

    public Packet getPacket() {
        return packet;
    }
}
