package views;

import controls.RoomPlayer;
import controls.Title;
import controls.PlayerState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Game;
import message.*;

public class GameLobby extends View {
    public GameLobby(Game game) {
        this.setPadding(new Insets(32, 32, 32, 32));

        RoomPlayer[] players = new RoomPlayer[4];

        for (int i = 0; i < 4; i++) {
            players[i] = new RoomPlayer();
        }

        game.getMessageLoop().registerNotifier(PlayerJoin.class, message -> {
            players[message.player().getId()].updateState(message.player().getName(), PlayerState.CONNECTED);
        });

        game.getMessageLoop().registerNotifier(PlayerLeave.class, message -> {
            players[message.player().getId()].updateState(message.player().getName(),
                    PlayerState.WAITING_FOR_CONNECTION);
        });

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> game.finish());
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        Pane startGame = Widgets.makeBigButton(Icon.GROUP, "Start Game");
        StackPane.setAlignment(startGame, Pos.BOTTOM_RIGHT);
        startGame.setMaxWidth(256);
        startGame.setOnMouseClicked(event -> {
            game.start();
        });

        menuContainer.getChildren().addAll(Widgets.makeLabel("Players"));

        for (int i = 0; i < 4; i++) {
            menuContainer.getChildren().add(players[i]);
        }

        menuContainer.getChildren().add(startGame);

        this.getChildren().addAll(new Title("Multiplayer Room"), menuContainer, backButton);
    }
}