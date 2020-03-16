package message;

import messageloop.Message;
import models.Game;
import network.PacketBuilder;
import network.PacketReader;

import java.io.IOException;

public class OnCountDown extends Message {
    private int time;

    public OnCountDown(int time) {
        this.time = time;
    }

    public int time() {
        return this.time;
    }

    @Override
    public void makePacket(PacketBuilder builder) {
        builder.withInt(time);
    }

    @Override
    public void readPacket(PacketReader reader, Game game) throws IOException {
        time = reader.readInt();
    }
}