package models.controller;

import models.Game;
import models.message.*;
import utils.StageManager;
import views.dialogs.InfoDialog;
import views.game.*;
import views.menu.Main;

public class ViewController {
    public ViewController(Game game) {
        game.getMessageLoop().registerNotifier(OnGameEnterLobby.class, message -> {
            StageManager.switchView(new Lobby(game));
        });

        game.getMessageLoop().registerNotifier(OnSelectTheme.class, message -> {
            if (game.getLocalPlayer().equals(message.player())) {
                StageManager.switchView(new SelectTheme(game, message.themes()));
            } else {
                StageManager.switchView(new OtherPlayerPlaying(game, message.player(), "selecting a theme"));
            }
        });

        game.getMessageLoop().registerNotifier(OnThemeSelected.class, message -> {
            if (game.getLocalPlayer().equals(message.player())) {
                StageManager.switchView(new MainGame(game, message.player()));
            } else {
                StageManager.switchView(new OtherPlayerPlaying(game, message.player(), "playing"));
            }
        });

        game.getMessageLoop().registerNotifier(GameDisconnected.class, message -> {
            new InfoDialog("Game stopped", "The server is disconnected!").show();
        });

        game.getMessageLoop().registerNotifier(OnGameFinished.class, message -> {
            StageManager.switchView(new FinishSinglePlayer(game));
        });

        game.getMessageLoop().registerNotifier(GameShutdown.class, message -> {
            StageManager.goBackTo(new Main());
        });
    }
}
