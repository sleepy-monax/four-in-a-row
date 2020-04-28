package models.message;

import java.io.IOException;

import models.Game;
import models.Player;
import models.messageloop.Message;
import network.PacketBuilder;
import network.PacketReader;

public class OnPlayerScoreChange extends Message {
    private Player player;
    private int score;
    private int level;
    private int levelMax;

    public OnPlayerScoreChange() {
    }

    public OnPlayerScoreChange(Player player, int score, int level, int levelMax) {
        this.player = player;
        this.score = score;
        this.level = level;
        this.levelMax = levelMax;
    }

    public Player player() {
        return player;
    }

    public int getScore() {
        return this.score;
    }

    public int getLevel() {
        return this.level;
    }

    public int getLevelMax() {
        return this.levelMax;
    }

    @Override
    public boolean repostable() {
        return false;
    }

    @Override
    public void makePacket(PacketBuilder builder) {
        builder.withInt(player.getId());
        
        builder.withInt(score);
        builder.withInt(level);
        builder.withInt(levelMax);
    }

    @Override
    public void readPacket(PacketReader reader, Game game) throws IOException {
        player = game.getPlayer(reader.readInt());

        score = reader.readInt();
        level = reader.readInt();
        levelMax = reader.readInt();
    }
}