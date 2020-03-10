package views;

import controls.RoomPlayer;
import controls.Title;
import dialogs.InfoDialog;
import controls.PlayerState;
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

public class PendingGame extends View {
    private RoomPlayer[] players;

    private Notifiable joinNotifier;
    private Notifiable leaveNotifier;
    private Notifiable stoppedNotifier;

    private Game game;

    public PendingGame(Game game) {
        this.game = game;
        this.setPadding(new Insets(32, 32, 32, 32));

        players = new RoomPlayer[4];

        for (int i = 0; i < 4; i++) {
            players[i] = new RoomPlayer();
        }

        joinNotifier = new Notifier<PlayerJoin>(PlayerJoin.class) {
            @Override
            public void handle(PlayerJoin message) {
                players[message.player().getId()].updateState(message.player().getName(), PlayerState.CONNECTED);
            }
        };

        leaveNotifier = new Notifier<PlayerLeave>(PlayerLeave.class) {
            @Override
            public void handle(PlayerLeave message) {
                players[message.player().getId()].updateState(message.player().getName(),
                        PlayerState.WAITING_FOR_CONNECTION);
            }
        };

        stoppedNotifier = new Notifier<GameDisconnected>(GameDisconnected.class) {
            @Override
            public void handle(GameDisconnected message) {
                new InfoDialog("Game stopped", "The server is disconnected!").show();
                StageManager.switchView(new MainMenu());
            }
        };

        game.getMessageLoop().registerNotifier(joinNotifier);
        game.getMessageLoop().registerNotifier(leaveNotifier);
        game.getMessageLoop().registerNotifier(stoppedNotifier);

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);

        Button backButton = Widgets.makeButton("Go back");
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        Pane joinButton = Widgets.makeBigButton(Icon.GROUP, "Start Game");
        StackPane.setAlignment(joinButton, Pos.BOTTOM_RIGHT);
        joinButton.setMaxWidth(256);

        menuContainer.getChildren().addAll(Widgets.makeLabel("Players"));

        for (int i = 0; i < 4; i++) {
            menuContainer.getChildren().add(players[i]);
        }

        menuContainer.getChildren().add(joinButton);

        this.getChildren().addAll(new Title("Multiplayer Room"), menuContainer, backButton);

        backButton.setOnAction(actionEvent -> {
            game.shutdown();
            StageManager.switchView(new MainMenu());
        });
    }

    @Override
    public void onSwitchOut() {
        game.getMessageLoop().registerNotifier(joinNotifier);
        game.getMessageLoop().registerNotifier(leaveNotifier);
        game.getMessageLoop().registerNotifier(stoppedNotifier);
    }
}
