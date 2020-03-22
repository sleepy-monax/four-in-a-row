package models.message;

import models.Game;
import models.Player;
import models.messageloop.Message;
import network.PacketBuilder;
import network.PacketReader;

import java.io.IOException;

public class OnPlayerAnswer extends Message {
    private Player player;
    private String answer;

    public OnPlayerAnswer() {
    }

    public OnPlayerAnswer(Player player, String answer) {
        this.player = player;
        this.answer = answer;
    }

    public Player player() {
        return player;
    }

    public String answer() {
        return answer;
    }

    @Override
    public void makePacket(PacketBuilder builder) {
        builder.withInt(player.getId());
        builder.withString(answer);
    }

    @Override
    public void readPacket(PacketReader reader, Game game) throws IOException {
        player = game.getPlayer(reader.readInt());
        answer = reader.readString();
    }
}
