package models.singleplayer;

import models.controller.GameController;
import models.Deck;
import models.Difficulty;
import models.Game;
import utils.SettingsManager;

public class SinglePlayer extends GameController {
    public SinglePlayer(Game game) {
        super(game);
    }

    public static void play() {
        Game game = new Game(Deck.load(), Difficulty.MEDIUM);
        SinglePlayer singleplayer = new SinglePlayer(game);
        game.setLocalPlayer(game.joinPlayer(SettingsManager.get().getPlayerName()));
        game.start();
    }
}
