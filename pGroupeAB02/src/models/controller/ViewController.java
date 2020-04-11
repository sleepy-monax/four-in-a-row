package models.controller;

import models.Game;
import models.Player;
import models.message.*;
import utils.StageManager;
import views.dialogs.InfoDialog;
import views.game.Lobby;
import views.game.MainGame;
import views.game.OtherPlayerPlaying;
import views.game.SelectTheme;
import views.menu.Main;

public class ViewController {
    private Player localPlayer;

    public ViewController(Game game) {
        game.getMessageLoop().registerNotifier(OnGameEnterLobby.class, message -> {
            StageManager.switchView(new Lobby(game));
        });

        game.getMessageLoop().registerNotifier(OnSelectTheme.class, message -> {
            if (localPlayer.equals(message.player())) {
                StageManager.switchView(new SelectTheme(game, message.themes()));
            } else {
                StageManager.switchView(new OtherPlayerPlaying(game, message.player(), "selecting a theme"));
            }
        });

        game.getMessageLoop().registerNotifier(OnThemeSelected.class, message -> {
            if (localPlayer.equals(message.player())) {
                StageManager.switchView(new MainGame(game));
            } else {
                StageManager.switchView(new OtherPlayerPlaying(game, message.player(), "playing"));
            }
        });

        game.getMessageLoop().registerNotifier(GameDisconnected.class, message -> {
            new InfoDialog("Game stopped", "The server is disconnected!").show();
        });

        game.getMessageLoop().registerNotifier(OnGameFinished.class, message -> {
            if (localPlayer.equals(message.player())) {
                StageManager.switchView(new Main());
            }
        });

        game.getMessageLoop().registerNotifier(OnRoundFinish.class, message -> {
            // FIXME: display scores
            // StageManager.switchView(new FinishSinglePlayer(game));
        });
    }

    public void setLocalPlayer(Player player) {
        localPlayer = player;
    }
}
