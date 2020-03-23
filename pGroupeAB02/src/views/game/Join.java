package views.game;

import javafx.scene.Node;
import views.TextStyle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.multiplayer.Multiplayer;
import utils.StageManager;
import utils.Icon;
import views.View;
import views.Widget;
import views.menu.Main;

public class Join extends View {
    public Join() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(32));

        TextField addressTextfield = Widget.textField("localhost");

        Node joinButton = Widget.buttonWithIcon(Icon.PEOPLE, "Connect", mouseEvent -> {
            Multiplayer.join(addressTextfield.getText(), Multiplayer.DEFAULT_PORT);
        });
        VBox.setMargin(joinButton, new Insets(16, 0, 0, 0));

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);

        Button backButton = Widget.button("Go back", actionEvent -> StageManager.switchView(new Main()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        menuContainer.getChildren().addAll(Widget.text("Host Address", TextStyle.SUBTITLE), addressTextfield, joinButton);

        this.getChildren().addAll(Widget.text("Join Multiplayer", TextStyle.TITLE), menuContainer, backButton);
    }
}
