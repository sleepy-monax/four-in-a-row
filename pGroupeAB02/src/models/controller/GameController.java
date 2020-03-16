package models.controller;

import javafx.application.Platform;
import models.message.*;
import models.Game;

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

        new AudioController(game);
        new ViewController(game);

        game.getMessageLoop().registerNotifier(OnGameFinished.class, message -> {
            tickService.cancel();
            shutdown();
        });

        tickTimer.schedule(tickService, 0, 1000);
    }

    public Game game() {
        return game;
    }

    public void shutdown() {
    }
}