package message;

import messageloop.Message;
import models.Game;
import models.Player;
import network.PacketBuilder;
import network.PacketReader;

import java.io.IOException;

public class OnNewClue extends Message {
    private Player player;
    private String clue;

    public OnNewClue() {
    }

    public OnNewClue(Player player, String clue) {
        this.player = player;
        this.clue = clue;
    }

    public Player player() {
        return player;
    }

    public String clue() {
        return clue;
    }

    @Override
    public void makePacket(PacketBuilder builder) {
        builder.withInt(player.getId());
        builder.withString(clue);
    }

    @Override
    public void readPacket(PacketReader reader, Game game) throws IOException {
        player = game.getPlayer(reader.readInt());
        clue = reader.readString();
    }
}
