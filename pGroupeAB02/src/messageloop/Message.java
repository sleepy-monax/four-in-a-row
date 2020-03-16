package messageloop;

import java.io.IOException;

import models.Game;
import network.Packet;
import network.PacketBuilder;
import network.PacketReader;
import network.PacketType;

public abstract class Message {
    public boolean repostable() {
        return true;
    }

    public final Packet asPacket() {
        PacketBuilder builder = new PacketBuilder(PacketType.GAME_MESSAGE);

        makePacket(builder);

        return builder.build();
    }

    public void makePacket(PacketBuilder builder) {
    }

    public static Message fromPacket(Packet packet, Game game) {
        if (packet.getPacketType() != PacketType.GAME_MESSAGE) {
            System.out.println("MESSAGE: fromPacket(): invalid packet type " + packet.getPacketType() + " expected "
                    + PacketType.GAME_MESSAGE);

            return null;
        }

        PacketReader reader = new PacketReader(packet);

        String messageClassName;

        try {
            messageClassName = reader.readString();
        } catch (IOException ex) {
            System.out.println("MESSAGE: fromPacket(): invalid packet format");
            ex.printStackTrace();

            return null;
        }

        Class messageClass = null;

        try {
            messageClass = Class.forName(messageClassName);

        } catch (ClassNotFoundException ex) {
            System.out.println("MESSAGE: fromPacket(): invalid message class " + messageClassName + "!");
            ex.printStackTrace();

            return null;
        }

        Message message;

        try {
            message = (Message) messageClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("MESSAGE: fromPacket(): failled to create message instance " + messageClassName + "!");
            ex.printStackTrace();

            return null;
        }

        try {
            message.readPacket(reader, game);
        } catch (IOException ex) {
            System.out.println("MESSAGE: readPacket(): invalid packet format for " + messageClassName);
            ex.printStackTrace();

            return null;
        }

        return message;
    }

    public void readPacket(PacketReader reader, Game game) throws IOException {
    }
}