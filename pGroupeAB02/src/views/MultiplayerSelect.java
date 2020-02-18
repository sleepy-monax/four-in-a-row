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

        Pane joinOnlineMultiplayer = Widgets.makeBigButton( "assets/multiplayer.png","Join Multiplayer");
        joinOnlineMultiplayer.setPadding(new Insets(0, 72, 0, 72));

        Pane createOnlineMultiplayer = Widgets.makeBigButton( "assets/multiplayer.png","Host Multiplayer");
        createOnlineMultiplayer.setPadding(new Insets(0, 72, 0, 72));

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> Main.switchScene(new MainMenu()));
        joinOnlineMultiplayer.setOnMouseClicked(mouseEvent -> Main.switchScene(new JoinMultiplayer()));
        createOnlineMultiplayer.setOnMouseClicked(mouseEvent -> Main.switchScene(new MultiplayerRoom()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        menuContainer.getChildren().addAll(
                Widgets.makeTitle("Multiplayer"),
                joinOnlineMultiplayer,
                createOnlineMultiplayer);

        getChildren().addAll(
                menuContainer,
                backButton);
    }
}
