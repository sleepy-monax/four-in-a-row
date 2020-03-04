package views;

import javax.swing.Spring;

import controller.AudioController;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import utils.StageManager;

public class SplashScreen extends View {
    public SplashScreen() {
        this.setId("background");
        this.setPadding(new Insets(32));

        Button button = Widgets.makeButton("Press any key to start...");
        StackPane.setAlignment(button, Pos.BOTTOM_CENTER);
        StackPane.setMargin(button, new Insets(96));

        Label labelRight = new Label("Groupe AB02\nL. De Laet, C. Lepine, N. Van Bossuyt\n\nRelease 1");
        labelRight.setTextAlignment(TextAlignment.CENTER);
        labelRight.setTextFill(new Color(1, 1, 1, 0.5));
        StackPane.setAlignment(labelRight, Pos.BOTTOM_CENTER);

        Pane spinner = new Pane();
        spinner.setId("wheel");
        spinner.setMinHeight(350);
        spinner.setMinWidth(350);
        spinner.setMaxHeight(350);
        spinner.setMaxWidth(350);
        spinner.setTranslateY(350);
        spinner.setScaleX(5);
        spinner.setScaleY(5);
        spinner.setScaleZ(5);
        spinner.setOpacity(0.25);

        StackPane.setAlignment(spinner, Pos.CENTER);

        RotateTransition animation = new RotateTransition(Duration.millis(16000), spinner);
        animation.setByAngle(360);
        animation.setCycleCount(Integer.MAX_VALUE);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.play();

        this.setOnKeyTyped(keyEvent -> {
            StageManager.goToMainMenu();
        });
        button.setOnAction(actionEvent -> {
            StageManager.goToMainMenu();
        });

        this.getChildren().addAll(spinner, Widgets.makeLogo(), button, labelRight);
    }
}
