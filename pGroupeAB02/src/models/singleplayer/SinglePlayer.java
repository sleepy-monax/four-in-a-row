package models.singleplayer;

import models.controller.GameController;
import models.Deck;
import models.Difficulty;
import models.Game;
import utils.Serialization;

public class SinglePlayer extends GameController {

    public SinglePlayer(Game game) {
        super(game);
        // TODO Auto-generated constructor stub
    }

    public static void play() {
        Game game = new Game(Deck.load(), Difficulty.MEDIUM);
        SinglePlayer singleplayer = new SinglePlayer(game);

        game.joinPlayer("Local Player");
        game.start();
    }
}
