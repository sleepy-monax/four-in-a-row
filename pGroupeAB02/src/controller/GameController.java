package controller;

import dialogs.InfoDialog;
import javafx.application.Platform;
import message.*;
import models.Game;
import utils.StageManager;
import views.GameLobby;
import views.MainGame;
import views.MainMenu;
import views.SelectTheme;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private final Game game;
    private final TimerTask tickService;

    public GameController(Game game) {
        this.game = game;

        Timer tickTimer = new Timer();

        tickService = new TimerTask() {
            @Override
            public void run() {
                // We want to run that on the main thread.
                Platform.runLater(() -> {
                    game().tick();
                });
            }
        };

        game.getMessageLoop().registerNotifier(OnGameEnterLobby.class, message -> {
            StageManager.switchView(new GameLobby(game));
        });

        game.getMessageLoop().registerNotifier(OnSelectTheme.class, message -> {
            StageManager.switchView(new SelectTheme(game, message.themes()));
        });

        game.getMessageLoop().registerNotifier(OnThemeSelected.class, message -> {
            StageManager.switchView(new MainGame(game));
        });

        game.getMessageLoop().registerNotifier(GameDisconnected.class, message -> {
            new InfoDialog("Game stopped", "The server is disconnected!").show();
        });

        game.getMessageLoop().registerNotifier(OnGameFinished.class, message -> {
            tickService.cancel();
            shutdown();
            StageManager.switchView(new MainMenu());
        });

        tickTimer.schedule(tickService, 0, 1000);
    }

    public Game game() {
        return game;
    }

    public void shutdown() {
    }
}