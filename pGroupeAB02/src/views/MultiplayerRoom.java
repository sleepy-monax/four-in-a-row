package views;

import controls.PlayerRoomControl;
import controls.PlayerRoomControlState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import master.MasterGame;
import models.GamePlayerListener;
import utils.Main;

import java.io.IOException;

public class MultiplayerRoom extends StackPane implements GamePlayerListener {
    PlayerRoomControl players[];

    public MultiplayerRoom() {
        this.setId("background");
        this.setPadding(new Insets(0, 32, 32, 32));

        players = new PlayerRoomControl[4];

        for (int i = 0; i < 4; i++) {
            players[i] = new PlayerRoomControl().updateState("", PlayerRoomControlState.WAITING_FOR_CONNECTION);
        }

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);

        Button backButton = Widgets.makeButton("Go back");
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        Pane joinButton = Widgets.makeBigButton("assets/multiplayer.png", "Start Game");
        StackPane.setAlignment(joinButton, Pos.BOTTOM_RIGHT);
        joinButton.setMaxWidth(256);

        menuContainer.getChildren().addAll(
                Widgets.makeTitle("Multiplayer Room"),
                Widgets.makeLabel("Players"));

        for (int i = 0; i < 4; i++) {
            menuContainer.getChildren().add(players[i]);
        }

        menuContainer.getChildren().add(joinButton);

        this.getChildren().addAll(menuContainer, backButton);

        try {
            MasterGame game = new MasterGame(null, 1234);
            game.setPlayerListener(this);

            backButton.setOnAction(actionEvent -> {
                Main.switchScene(new MultiplayerSelect());
                game.shutdown();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPlayerJoin(int index, String name) {
        players[index].updateState(name, PlayerRoomControlState.CONNECTED);
    }

    @Override
    public void onPlayerLeave(int index, String name) {
        players[index].updateState(name, PlayerRoomControlState.WAITING_FOR_CONNECTION);
    }
}
