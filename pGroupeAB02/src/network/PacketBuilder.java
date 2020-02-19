package network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PacketBuilder {

    private final ByteArrayOutputStream byteArrayOutputStream;
    private final DataOutputStream dataOutputStream;

    private final PacketType packetType;
    private short packetID;
    private boolean isBuilt;

    public PacketBuilder(final PacketType packetType) {
        byteArrayOutputStream = new ByteArrayOutputStream();
        dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        isBuilt = false;

        this.packetType = packetType;
        packetID = 0;
    }

    private void checkBuilt() {
        if (isBuilt) throw new IllegalStateException("Packet already built");
    }

    public synchronized PacketBuilder withID(final short packetID) {
        checkBuilt();
        this.packetID = packetID;
        return this;
    }

    public synchronized PacketBuilder withByte(final byte b) {
        checkBuilt();
        try {
            dataOutputStream.writeByte(b);
        } catch (final IOException e) {
        }
        return this;
    }

    public synchronized PacketBuilder withBytes(final byte[] b) {
        checkBuilt();
        try {
            dataOutputStream.writeInt(b.length);
            dataOutputStream.write(b);
        } catch (final IOException e) {
        }
        return this;
    }

    public synchronized PacketBuilder withInt(final int i) {
        checkBuilt();
        try {
            dataOutputStream.writeInt(i);
        } catch (final IOException e) {
        }
        return this;
    }

    public synchronized PacketBuilder withString(final String s) {
        withBytes(s.getBytes(StandardCharsets.UTF_8));

        return this;
    }

    public synchronized PacketBuilder withBoolean(final boolean b) {
        checkBuilt();
        try {
            dataOutputStream.writeBoolean(b);
        } catch (final IOException e) {
        }
        return this;
    }

    public synchronized PacketBuilder withFloat(final float f) {
        checkBuilt();
        try {
            dataOutputStream.writeFloat(f);
        } catch (final IOException e) {
        }
        return this;
    }

    public synchronized PacketBuilder withDouble(final double d) {
        checkBuilt();
        try {
            dataOutputStream.writeDouble(d);
        } catch (final IOException e) {
        }

        return this;
    }

    public synchronized PacketBuilder withLong(final long l) {
        checkBuilt();
        try {
            dataOutputStream.writeLong(l);
        } catch (final IOException e) {
        }

        return this;
    }

    public synchronized PacketBuilder withShort(final short s) {
        checkBuilt();
        try {
            dataOutputStream.writeShort(s);
        } catch (final IOException e) {
        }

        return this;
    }

    public synchronized byte[] getBytes() {
        return byteArrayOutputStream.toByteArray();
    }

    public synchronized Packet build() {
        checkBuilt();
        isBuilt = true;

        try {
            dataOutputStream.close();
        } catch (final IOException e) {
        }

        return new Packet(
                packetType,
                packetID,
                byteArrayOutputStream.toByteArray()
        );
    }
}
