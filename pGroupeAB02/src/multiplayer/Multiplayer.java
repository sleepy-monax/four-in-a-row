package multiplayer;

import models.Game;
import utils.StageManager;
import views.PendingGame;

public class Multiplayer {
    public static final int DEFAULT_PORT = 1234;

    public static void join(String username, String ip, int port) {
        Game game = new Slave(username, ip, port);

        PendingGame view = new PendingGame(game);

        StageManager.switchView(view);
    }

    public static void host(int port) {
        Game game = new Master(null, port);

        PendingGame view = new PendingGame(game);

        StageManager.switchView(view);
    }
}