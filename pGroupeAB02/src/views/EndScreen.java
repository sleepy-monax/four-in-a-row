package views;

import controller.AudioController;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import utils.StageManager;

public class EndScreen extends View {

    public EndScreen() {

        Pane background = new Pane();
        background.setStyle("-fx-background-color: black;");
        background.setOpacity(0);

        Parent logo = Widgets.makeLogo();

        TranslateTransition translate = new TranslateTransition();
        translate.setDelay(Duration.seconds(2));
        translate.setDuration(Duration.seconds(0.5));
        translate.setFromY(0);
        translate.setToY(1920);
        translate.setNode(logo);

        FadeTransition fade = new FadeTransition();
        fade.setDelay(Duration.seconds(0.5));
        fade.setDuration(Duration.seconds(0.5));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setNode(background);

        this.getChildren().addAll(background, logo);

        AudioController.playNow("assets/end.wav", () -> {
            StageManager.quit();
        });
        translate.play();
        fade.play();
    }

}