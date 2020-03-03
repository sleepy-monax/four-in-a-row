package message;

import messageloop.Message;
import models.Player;

public class PlayerJoin implements Message {
    private Player player;

    public PlayerJoin(Player player) {
        this.player = player;
    }

    @Override
    public boolean repostable() {
        return true;
    }

    public Player player() {
        return player;
    }
}