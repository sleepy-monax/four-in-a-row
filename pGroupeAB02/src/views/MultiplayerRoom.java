package views;

import controls.PlayerRoomControl;
import controls.PlayerRoomControlState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.Main;

public class MultiplayerRoom extends StackPane {
    public MultiplayerRoom()
    {
        this.setId("background");
        this.setPadding(new Insets(0, 32, 32, 32));

        PlayerRoomControl p0 = new PlayerRoomControl().updateState("Nicolas", PlayerRoomControlState.CONNECTED);
        PlayerRoomControl p1 = new PlayerRoomControl().updateState("Cédrique", PlayerRoomControlState.CONNECTING);
        PlayerRoomControl p2 = new PlayerRoomControl().updateState("jean", PlayerRoomControlState.CONNECTING);
        PlayerRoomControl p3 = new PlayerRoomControl().updateState("", PlayerRoomControlState.WAITING_FOR_CONNECTION);

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> Main.switchScene(new MultiplayerSelect()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        Pane joinButton = Widgets.makeBigButton("assets/multiplayer.png", "Start Game");
        StackPane.setAlignment(joinButton, Pos.BOTTOM_RIGHT);
        joinButton.setMaxWidth(256);

        menuContainer.getChildren().addAll(
                Widgets.makeTitle("Multiplayer Room"),
                Widgets.makeLabel("Players"),
                p0, p1, p2, p3,
                joinButton);

        this.getChildren().addAll(menuContainer, backButton);
    }
}
