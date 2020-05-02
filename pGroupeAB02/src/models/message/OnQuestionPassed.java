package models.message;

import models.Game;
import models.Player;
import models.messageloop.Message;
import network.PacketBuilder;
import network.PacketReader;

import java.io.IOException;

public class OnQuestionPassed extends Message {
    private Player player;

    public OnQuestionPassed() {
    }

    public OnQuestionPassed(Player player) {
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
