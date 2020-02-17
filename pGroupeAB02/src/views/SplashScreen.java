package views;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import utils.Main;

public class SplashScreen extends StackPane {
    public SplashScreen()
    {
        this.setId("background");
        this.setPadding(new Insets(32));

        Pane logo = new Pane();
        logo.setId("logo");

        Button button = Widgets.makeButton("Press any key to start...");
        StackPane.setAlignment(button, Pos.BOTTOM_CENTER);
        StackPane.setMargin(button, new Insets(96));

        Label labelRight = new Label("© 2020 De Laet L. Lepine C. Van Bossuyt N.\nAll right reserved.\n\nRelease 1");
        labelRight.setTextAlignment(TextAlignment.CENTER);
        labelRight.setTextFill(new Color(1, 1, 1, 0.5));
        StackPane.setAlignment(labelRight, Pos.BOTTOM_CENTER);

        this.setOnKeyTyped(keyEvent -> Main.switchScene(new MainMenu()));
        button.setOnAction(actionEvent -> Main.switchScene(new MainMenu()));

        this.getChildren().addAll(logo, button, labelRight);
    }
}
