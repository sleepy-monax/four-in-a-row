package models.multiplayer;

import models.Player;

public class ConnectedSlave {
    private Player player = null;

    public ConnectedSlave() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
