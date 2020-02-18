package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.Main;

public class MultiplayerSelect extends StackPane {
    public MultiplayerSelect()
    {
        this.setAlignment(Pos.CENTER);
        this.setId("background");
        this.setPadding(new Insets(32));

        Pane localMultiplayerButton = Widgets.makeBigButton("assets/singleplayer.png", "Local Multiplayer" );
        localMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane joinOnlineMultiplayer = Widgets.makeBigButton( "assets/multiplayer.png","Join Multiplayer");
        joinOnlineMultiplayer.setPadding(new Insets(0, 72, 0, 72));

        Pane createOnlineMultiplayer = Widgets.makeBigButton( "assets/multiplayer.png","Host Multiplayer");
        createOnlineMultiplayer.setPadding(new Insets(0, 72, 0, 72));

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(436);

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> Main.switchScene(new MainMenu()));
        joinOnlineMultiplayer.setOnMouseClicked(mouseEvent -> Main.switchScene(new JoinMultiplayer()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        menuContainer.getChildren().addAll(
                Widgets.makeTitle("Multiplayer"),
                localMultiplayerButton,
                joinOnlineMultiplayer,
                createOnlineMultiplayer);

        getChildren().addAll(
                menuContainer,
                backButton);
    }
}
