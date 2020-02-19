package master;

import models.Player;

public class Slave {
    private Player player;

    public Slave(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
