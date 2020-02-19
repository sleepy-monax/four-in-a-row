package master;

import models.Player;

public class Slave {
    private Player player = null;

    public Slave() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
