package models.message;

import models.Game;
import models.Player;
import models.messageloop.Message;
import network.PacketBuilder;
import network.PacketReader;

import java.io.IOException;

public class OnThemeSelected extends Message {
    private Player player;
    private String theme;

    public OnThemeSelected() {
    }

    public OnThemeSelected(Player player, String theme) {
        this.player = player;
        this.theme = theme;
    }

    public Player player() {
        return player;
    }

    public String theme() {
        return this.theme;
    }

    @Override
    public void makePacket(PacketBuilder builder) {
        builder.withInt(player.getId());
        builder.withString(theme);
    }

    @Override
    public void readPacket(PacketReader reader, Game game) throws IOException {
        player = game.getPlayer(reader.readInt());
        theme = reader.readString();
    }
}