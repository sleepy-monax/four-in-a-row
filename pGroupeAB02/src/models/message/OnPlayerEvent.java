package models.message;

import models.Game;
import models.Player;
import models.messageloop.Message;
import network.PacketBuilder;
import network.PacketReader;

import java.io.IOException;

public class OnPlayerEvent extends Message {
    private Player player;
    private PlayerEvent event;

    public OnPlayerEvent() {
    }

    public OnPlayerEvent(Player player, PlayerEvent event) {
        this.player = player;
        this.event = event;
    }

    public Player player() {
        return player;
    }

    public PlayerEvent event() {
        return event;
    }

    @Override
    public boolean repostable() {
        return true;
    }

    @Override
    public void makePacket(PacketBuilder builder) {
        builder.withInt(player.getId());
        builder.withInt(event.ordinal());
    }

    @Override
    public void readPacket(PacketReader reader, Game game) throws IOException {
        player = game.getPlayer(reader.readInt());
        event = PlayerEvent.values()[reader.readInt()];
    }
}