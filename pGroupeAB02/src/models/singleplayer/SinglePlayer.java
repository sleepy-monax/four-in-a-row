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
        Deck deck = Serialization.readFromJsonFile("data/question.json", Deck.class);
        Game game = new Game(deck, Difficulty.MEDIUM);
        SinglePlayer singleplayer = new SinglePlayer(game);

        game.joinPlayer("Local Player");
        game.start();
    }
}
