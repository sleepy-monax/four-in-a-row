package models.message;

import models.Game;
import models.messageloop.Message;
import network.PacketBuilder;
import network.PacketReader;

import java.io.IOException;

public class OnSelectTheme extends Message {
    private String[] themes;

    public OnSelectTheme() {
    }

    public OnSelectTheme(String[] themes) {
        this.themes = themes;
    }

    public String[] themes() {
        return this.themes;
    }

    @Override
    public void makePacket(PacketBuilder builder) {
        builder.withInt(themes.length);

        for (String theme : themes) {
            builder.withString(theme);
        }
    }

    @Override
    public void readPacket(PacketReader reader, Game game) throws IOException {
        int themeCount = reader.readInt();

        themes = new String[themeCount];

        for (int i = 0; i < themeCount; i++) {
            themes[i] = reader.readString();
        }
    }
}