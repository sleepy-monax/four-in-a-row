package views.game;

import javafx.scene.Node;
import views.TextStyle;
import views.widgets.PlayerState;
import views.widgets.RoomPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import models.message.OnPlayerEvent;
import models.message.PlayerEvent;
import models.Game;
import utils.Icon;
import views.View;
import views.Widget;

import static views.Layout.*;
import static views.Widget.*;

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


        Node startGameButton = Widget.buttonWithIcon(Icon.GROUP, "Start Game", event -> {
            game.start();
        });


        Region lobbyPanel = panel(
            vertical(
                16,
                horizontallyCentered(Widget.text("Multiplayer Lobby", TextStyle.TITLE)),
                spacer(16),
                text("Players", TextStyle.LABEL),
                vertical(8, players),
                spacer(16),
                horizontal(
                    8,
                    fill(verticallyCentered(startGameButton)),
                    verticallyCentered(iconButton(Icon.SETTINGS, event->{}))
                )
            )
        );

        Button backButton = Widget.button("Go back", actionEvent -> game.shutdown());
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(Widget.text("Multiplayer Room", TextStyle.SUBTITLE), verticallyCentered(width(512,lobbyPanel)), backButton);
    }
}
