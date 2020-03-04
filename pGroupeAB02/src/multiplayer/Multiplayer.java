package multiplayer;

import models.Game;
import utils.StageManager;
import views.PendingGameView;

public class Multiplayer {
    public static final int DEFAULT_PORT = 1234;

    public static void join(String username, String ip, int port) {
        Game game = new SlaveGame(username, ip, port);

        PendingGameView view = new PendingGameView(game);

        StageManager.switchView(view);
    }

    public static void host(int port) {
        Game game = new MasterGame(null, port);

        PendingGameView view = new PendingGameView(game);

        StageManager.switchView(view);
    }
}