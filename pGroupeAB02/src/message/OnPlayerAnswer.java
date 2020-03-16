package message;

import java.io.IOException;

import messageloop.Message;
import models.Game;
import models.Player;
import network.PacketBuilder;
import network.PacketReader;

public class OnPlayerAnswer extends Message {
    private Player player;
    private String answer;

    public OnPlayerAnswer(Player player, String answer) {
        this.answer = answer;
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
