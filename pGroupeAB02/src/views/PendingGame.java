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

public class PendingGame extends View {
    private PlayerRoomControl[] players;

    private Notifiable joinNotifier;
    private Notifiable leaveNotifier;
    private Notifiable stoppedNotifier;

    private Game game;

    public PendingGame(Game game) {
        this.game = game;
        this.setPadding(new Insets(0, 32, 32, 32));

        players = new PlayerRoomControl[4];

        for (int i = 0; i < 4; i++) {
            players[i] = new PlayerRoomControl();
        }

        joinNotifier = new Notifier<PlayerJoin>(PlayerJoin.class) {
            @Override
            public void handle(PlayerJoin message) {
                players[message.player().getId()].updateState(message.player().getName(),
                        PlayerRoomControlState.CONNECTED);
            }
        };

        leaveNotifier = new Notifier<PlayerLeave>(PlayerLeave.class) {
            @Override
            public void handle(PlayerLeave message) {
                players[message.player().getId()].updateState(message.player().getName(),
                        PlayerRoomControlState.WAITING_FOR_CONNECTION);
            }
        };

        stoppedNotifier = new Notifier<GameDisconnected>(GameDisconnected.class) {
            @Override
            public void handle(GameDisconnected message) {
                StageManager.switchView(new Information("Game Stopped", "The Server is disconnected!"));
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

        menuContainer.getChildren().addAll(Widgets.makeTitle("Multiplayer Room"), Widgets.makeLabel("Players"));

        for (int i = 0; i < 4; i++) {
            menuContainer.getChildren().add(players[i]);
        }

        menuContainer.getChildren().add(joinButton);

        this.getChildren().addAll(menuContainer, backButton);

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
