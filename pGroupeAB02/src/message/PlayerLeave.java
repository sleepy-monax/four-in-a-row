package message;

import messageloop.Message;
import models.Player;

public class PlayerLeave extends Message {
    private Player player;

    public PlayerLeave(Player player) {
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