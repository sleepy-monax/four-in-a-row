package views;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import utils.StageManager;

public class SplashScreen extends View {
    public SplashScreen() {
        this.setPadding(new Insets(32));

        Button button = Widgets.makeButton("Press any key to start...");
        StackPane.setAlignment(button, Pos.BOTTOM_CENTER);
        StackPane.setMargin(button, new Insets(96));
        button.setTranslateY(300);

        Label labelRight = new Label("Groupe AB02\nL. De Laet, C. Lepine, N. Van Bossuyt\n\nRelease 1");
        labelRight.setTextAlignment(TextAlignment.CENTER);
        labelRight.setTextFill(new Color(1, 1, 1, 0.5));
        labelRight.setTranslateY(300);
        StackPane.setAlignment(labelRight, Pos.BOTTOM_CENTER);

        this.setOnKeyTyped(keyEvent -> {
            StageManager.goToMainMenu();
        });
        button.setOnAction(actionEvent -> {
            StageManager.goToMainMenu();
        });

        TranslateTransition transitionBtn = new TranslateTransition();
        transitionBtn.setDelay(Duration.seconds(1));
        transitionBtn.setDuration(Duration.seconds(0.5));
        transitionBtn.setFromY(300);
        transitionBtn.setToY(0);
        transitionBtn.setNode(button);
        transitionBtn.play();

        TranslateTransition transitionLbl = new TranslateTransition();
        transitionLbl.setDelay(Duration.seconds(1));
        transitionLbl.setDuration(Duration.seconds(0.75));
        transitionLbl.setFromY(300);
        transitionLbl.setToY(0);
        transitionLbl.setNode(labelRight);
        transitionLbl.play();

        this.getChildren().addAll(Widgets.makeLogo(), button, labelRight);
    }
}
