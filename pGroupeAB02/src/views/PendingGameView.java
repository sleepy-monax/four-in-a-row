package views;

import controls.PlayerRoomControl;
import controls.PlayerRoomControlState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Game;
import utils.StageManager;
import messageloop.*;
import message.*;

public class PendingGameView extends StackPane {
    private PlayerRoomControl[] players;
    private Game game;

    public PendingGameView(Game game) {
        this.game = game;
        this.setId("background");
        this.setPadding(new Insets(0, 32, 32, 32));

        players = new PlayerRoomControl[4];

        for (int i = 0; i < 4; i++) {
            PlayerRoomControl control = new PlayerRoomControl();

            final int playerIndex = i;
            Notifiable joinNotifier = new Notifier<PlayerJoin>(PlayerJoin.class) {

                @Override
                public void handle(PlayerJoin message) {
                    if (message.player().getId() == playerIndex) {
                        control.updateState(message.player().getName(), PlayerRoomControlState.CONNECTED);
                    }
                }
            };

            Notifiable leaveNotifier = new Notifier<PlayerLeave>(PlayerLeave.class) {
                @Override
                public void handle(PlayerLeave message) {
                    if (message.player().getId() == playerIndex) {
                        control.updateState(message.player().getName(), PlayerRoomControlState.WAITING_FOR_CONNECTION);
                    }
                }
            };

            players[i] = control;

            game.getMessageLoop().registerNotifier(joinNotifier);
            game.getMessageLoop().registerNotifier(leaveNotifier);
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

        backButton.setOnAction(actionEvent -> {
            game.shutdown();
            StageManager.switchScene(new MainMenu());
        });
    }
}
