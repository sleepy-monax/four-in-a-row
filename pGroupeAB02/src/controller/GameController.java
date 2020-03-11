package controller;

import java.util.Timer;
import java.util.TimerTask;

import dialogs.InfoDialog;
import javafx.application.Platform;
import message.GameDisconnected;
import message.OnGameEnterLobby;
import message.OnGameFinished;
import message.OnSelectTheme;
import message.OnThemeSelected;
import models.Game;
import utils.StageManager;
import views.MainMenu;
import views.GameLobby;
import views.MainGame;
import views.SelectTheme;

public class GameController {
    private final Game game;
    private final Timer tickTimer;
    private final TimerTask tickService;

    public GameController(Game game) {
        this.game = game;

        tickTimer = new Timer();
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