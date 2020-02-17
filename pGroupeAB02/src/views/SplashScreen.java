package views;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import utils.Main;

public class SplashScreen extends StackPane {
    public SplashScreen()
    {
        this.setId("background");
        this.setPadding(new Insets(64));

        Pane logo = new Pane();
        logo.setId("logo");

        Button button = Widgets.makeButton("Press any key to start...");
        StackPane.setAlignment(button, Pos.BOTTOM_CENTER);

        this.setOnKeyTyped(keyEvent -> Main.switchScene(new MainMenu()));
        button.setOnAction(actionEvent -> Main.switchScene(new MainMenu()));

        this.getChildren().addAll(logo, button);
    }
}
