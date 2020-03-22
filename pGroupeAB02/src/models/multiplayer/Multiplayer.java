package models.multiplayer;

import views.dialogs.InfoDialog;
import models.Deck;
import models.Difficulty;
import models.Game;
import utils.Serialization;

public class Multiplayer {
    public static final int DEFAULT_PORT = 1234;

    public static void join(String username, String ip, int port) {
        Game game = new Game(null, Difficulty.EASY);
        Slave slave = new Slave(game, username, ip, port);

        if (slave.connect()) {
            game.startPassive();

        } else {
            new InfoDialog("Join models.multiplayer", "Failed to reach " + ip + ":" + port).show();
            game.finish();
        }
    }

    public static void host(int port) {
        Game game = new Game(Deck.load(), Difficulty.EASY);
        Master master = new Master(game, port);

        game.joinPlayer("Local Player");
        game.enterLobby();
    }
}