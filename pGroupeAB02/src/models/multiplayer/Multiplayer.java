package models.multiplayer;

import utils.SettingsManager;
import views.dialogs.InfoDialog;
import models.Deck;
import models.Difficulty;
import models.Game;
import models.states.Pending;

public class Multiplayer {
    public static final int DEFAULT_PORT = 1234;

    public static void join(String ip, int port, String password) {
        Game game = new Game(null, Difficulty.EASY);
        Slave slave = new Slave(game, SettingsManager.get().getPlayerName(), ip, port, password);

        if (slave.connect()) {
            game.startWidthState(slave.getPassiveState());
        } else {
            new InfoDialog("Join Multiplayer", "Failed to reach " + ip + ":" + port).show();
            game.shutdown();
        }
    }

    public static void host(int port, String password) {
        Game game = new Game(Deck.get(), Difficulty.MEDIUM);
        Master master = new Master(game, port, password);

        game.setLocalPlayer(game.joinPlayer(SettingsManager.get().getPlayerName()));
        game.startWidthState(new Pending());
        game.enterLobby();
    }
}