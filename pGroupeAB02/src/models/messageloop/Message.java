package models.messageloop;

import models.Game;
import network.Packet;
import network.PacketBuilder;
import network.PacketReader;
import network.PacketType;

import java.io.IOException;

public abstract class Message {
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

        Class<?> messageClass;

        try {
            messageClass = Class.forName(messageClassName);

        } catch (ClassNotFoundException ex) {
            System.out.println("MESSAGE: fromPacket(): invalid models.message class " + messageClassName + "!");
            ex.printStackTrace();

            return null;
        }

        Message message;

        try {
            message = (Message) messageClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("MESSAGE: fromPacket(): failled to create models.message instance " + messageClassName + "!");
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

    public boolean repostable() {
        return true;
    }

    public boolean confined()    {
        return false;
    }

    public final Packet asPacket() {
        PacketBuilder builder = new PacketBuilder(PacketType.GAME_MESSAGE);

        builder.withString(this.getClass().getName());

        makePacket(builder);

        return builder.build();
    }

    public void makePacket(PacketBuilder builder) {
    }

    public void readPacket(PacketReader reader, Game game) throws IOException {
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}