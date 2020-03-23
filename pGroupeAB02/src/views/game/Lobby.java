package views.game;

import javafx.scene.Node;
import views.TextStyle;
import views.widgets.PlayerState;
import views.widgets.RoomPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.message.OnPlayerEvent;
import models.message.PlayerEvent;
import models.Game;
import utils.Icon;
import views.View;
import views.Widget;

public class Lobby extends View {
    public Lobby(Game game) {
        this.setPadding(new Insets(32, 32, 32, 32));

        RoomPlayer[] players = new RoomPlayer[4];

        for (int i = 0; i < 4; i++) {
            players[i] = new RoomPlayer();
        }

        game.getMessageLoop().registerNotifier(OnPlayerEvent.class, message -> {

            if (message.event() == PlayerEvent.JOIN) {
                players[message.player().getId()].updateState(message.player().getName(), PlayerState.CONNECTED);
            } else if (message.event() == PlayerEvent.LEAVE) {
                players[message.player().getId()].updateState(message.player().getName(),
                        PlayerState.WAITING_FOR_CONNECTION);
            }
        });

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);

        Button backButton = Widget.button("Go back", actionEvent -> game.finish());
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        Node startGame = Widget.buttonWithIcon(Icon.GROUP, "Start Game", event -> {
            game.start();
        });

        StackPane.setAlignment(startGame, Pos.BOTTOM_RIGHT);

        menuContainer.getChildren().addAll(Widget.text("Players", TextStyle.SUBTITLE));

        for (int i = 0; i < 4; i++) {
            menuContainer.getChildren().add(players[i]);
        }

        menuContainer.getChildren().add(startGame);

        this.getChildren().addAll(Widget.text("Multiplayer Room", TextStyle.SUBTITLE), menuContainer, backButton);
    }
}
