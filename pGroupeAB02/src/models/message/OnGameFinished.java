package models.message;

import java.io.IOException;

import models.Game;
import models.Player;
import models.messageloop.Message;
import network.PacketBuilder;
import network.PacketReader;

public class OnGameFinished extends Message {
    private Player player;

    public OnGameFinished() {
    }

    public OnGameFinished(Player player) {
        this.player = player;
    }

    public Player player() {
        return player;
    }

    @Override
    public void makePacket(PacketBuilder builder) {
        builder.withInt(player.getId());
    }

    @Override
    public void readPacket(PacketReader reader, Game game) throws IOException {
        player = game.getPlayer(reader.readInt());
    }
}