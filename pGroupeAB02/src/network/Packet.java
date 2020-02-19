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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet {
    private final PacketType packetType;
    private final short packetID;
    private final int dataLength;
    private final byte[] data;

    public Packet(final PacketType packetType, final short packetID, final byte[] data) {
        this.packetType = packetType;
        this.packetID = packetID;
        dataLength = data.length;
        this.data = data;
    }

    public static Packet fromStream(final DataInputStream in) throws IOException {
        // Packet Type
        final PacketType packetType = PacketType.fastValues[in.readByte()];

        // Packet ID
        final short packetID = in.readShort();

        // Data Length
        final int dataLength = in.readInt();

        // Data
        final byte[] data = new byte[dataLength];
        in.readFully(data);

        return new Packet(
                packetType,
                packetID,
                data
        );
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public short getPacketID() {
        return packetID;
    }

    public int getDataLength() {
        return dataLength;
    }

    public byte[] getData() {
        return data;
    }

    public void write(final DataOutputStream out) throws IOException {
        // Packet Type
        out.writeByte(packetType.ordinal());

        // Packet ID
        out.writeShort(packetID);

        // Data Length
        out.writeInt(dataLength);

        // Data
        out.write(data);
    }

    @Override
    public String toString() {
        return "Type: [" + packetType + "] ID: [" + packetID + "] Data: [" + dataLength + " bytes]";
    }
}
