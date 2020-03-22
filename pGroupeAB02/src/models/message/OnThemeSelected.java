package models.message;

import models.Game;
import models.messageloop.Message;
import network.PacketBuilder;
import network.PacketReader;

import java.io.IOException;

public class OnThemeSelected extends Message {
    private String theme;

    public OnThemeSelected() {
    }

    public OnThemeSelected(String theme) {
        this.theme = theme;
    }

    public String theme() {
        return this.theme;
    }

    @Override
    public void makePacket(PacketBuilder builder) {
       builder.withString(theme);
    }

    @Override
    public void readPacket(PacketReader reader, Game game) throws IOException {
        theme = reader.readString();
    }
}