package multiplayer;

import models.Deck;
import models.Difficulty;
import models.Game;
import utils.Serialization;

public class Multiplayer {
    public static final int DEFAULT_PORT = 1234;

    public static void join(String username, String ip, int port) {
        Game game = new Game(null, Difficulty.EASY);
        Slave slave = new Slave(game, ip, port);

        game.joinPlayer(username);
        game.startPassive();
    }

    public static void host(int port) {
        Deck deck = Serialization.readFromJsonFile("data/question.json", Deck.class);
        Game game = new Game(deck, Difficulty.EASY);
        Master master = new Master(game, port);

        game.joinPlayer("Local Player");
        game.enterLobby();
    }
}