package views.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import models.Deck;
import utils.StageManager;
import views.Layout;
import views.TextStyle;
import views.View;
import views.Widget;

import static views.Layout.*;
import static views.Widget.*;

public class AdminConnect extends View {


    public AdminConnect() {
        setPadding(new Insets(32));


        Node menu =
                vertical(
                        16,
                        text("Username", TextStyle.SUBTITLE),
                        usernameField(),
                        text("Password", TextStyle.SUBTITLE),
                        passwordField(),
                        button("Connect", event -> {
                            StageManager.switchView(new Editor(Deck.load()));
                        })
                );


        Button backButton = Widget.button("Go back", actionEvent -> StageManager.switchView(new Main()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        getChildren().addAll(Widget.text("Connect", TextStyle.TITLE), Layout.verticallyCentered(Layout.width(512, Widget.panel(menu))), backButton);
    }
}
