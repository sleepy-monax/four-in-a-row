package models.singleplayer;

import models.Deck;
import models.Difficulty;
import models.Game;
import models.controller.GameController;
import utils.SettingsManager;

public class SinglePlayer extends GameController {
    public SinglePlayer(Game game) {
        super(game);
    }

    public static void play() {
        Game game = new Game(Deck.get(), Difficulty.MEDIUM);
        SinglePlayer singleplayer = new SinglePlayer(game);
        game.setLocalPlayer(game.joinPlayer(SettingsManager.get().getPlayerName()));
        game.start();
    }
}
