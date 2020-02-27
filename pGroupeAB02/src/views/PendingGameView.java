package views;

import controls.PlayerRoomControl;
import controls.PlayerRoomControlState;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.PendingGame;
import models.PendingGameListener;
import utils.StageManager;

public class PendingGameView extends StackPane implements PendingGameListener {
    private PlayerRoomControl[] players;
    private PendingGame pendingGame;

    public PendingGameView(PendingGame pendingGame) {
        this.pendingGame = pendingGame;
        this.setId("background");
        this.setPadding(new Insets(0, 32, 32, 32));

        players = new PlayerRoomControl[4];

        for (int i = 0; i < 4; i++) {
            players[i] = new PlayerRoomControl(pendingGame, i).updateState("",
                    PlayerRoomControlState.WAITING_FOR_CONNECTION);
        }

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);

        Button backButton = Widgets.makeButton("Go back");
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        Pane joinButton = Widgets.makeBigButton("assets/multiplayer.png", "Start Game");
        StackPane.setAlignment(joinButton, Pos.BOTTOM_RIGHT);
        joinButton.setMaxWidth(256);

        menuContainer.getChildren().addAll(Widgets.makeTitle("Multiplayer Room"), Widgets.makeLabel("Players"));

        for (int i = 0; i < 4; i++) {
            menuContainer.getChildren().add(players[i]);
        }

        menuContainer.getChildren().add(joinButton);

        this.getChildren().addAll(menuContainer, backButton);

        pendingGame.attachListener(this);

        backButton.setOnAction(actionEvent -> {
            pendingGame.shutdown();
            StageManager.switchScene(new MainMenu());
        });
    }

    @Override
    public void onPlayerJoin(int index, String name) {
        Platform.runLater(() -> {
            players[index].updateState(name, PlayerRoomControlState.CONNECTED);
        });
    }

    @Override
    public void onPlayerLeave(int index, String name) {
        Platform.runLater(() -> {
            players[index].updateState(name, PlayerRoomControlState.WAITING_FOR_CONNECTION);
        });
    }

    @Override
    public void onGameTick() {
        for (int i = 0; i < 4; i++) {
            players[i].updateState(this.pendingGame.getPlayerName(i), this.pendingGame.getPlayerState(i));
        }
    }
}
