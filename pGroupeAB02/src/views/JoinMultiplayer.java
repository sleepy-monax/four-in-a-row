package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import multiplayer.Multiplayer;
import utils.StageManager;

public class JoinMultiplayer extends View {
    public JoinMultiplayer() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(32));

        TextField userNameTextfield = Widgets.makeTextField("User");

        TextField addressTextfield = Widgets.makeTextField("localhost");

        Pane joinButton = Widgets.makeBigButton("assets/multiplayer.png", "Connect");
        joinButton.setPadding(new Insets(0, 72, 0, 72));
        joinButton.setOnMouseClicked(mouseEvent -> {
            Multiplayer.join(userNameTextfield.getText(), addressTextfield.getText(), Multiplayer.DEFAULT_PORT);
        });
        VBox.setMargin(joinButton, new Insets(16, 0, 0, 0));

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> StageManager.switchView(new MainMenu()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        menuContainer.getChildren().addAll(Widgets.makeTitle("Join Multiplayer"), Widgets.makeLabel("Username"),
                userNameTextfield, Widgets.makeLabel("Host Address"), addressTextfield, joinButton);

        this.getChildren().addAll(menuContainer, backButton);
    }
}
