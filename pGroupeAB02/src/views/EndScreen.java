package views;

import controller.AudioController;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import utils.StageManager;

public class EndScreen extends View {

    public EndScreen() {

        Pane background = new Pane();
        background.setStyle("-fx-background-color: black;");
        background.setOpacity(0);

        Parent logo = Widgets.makeLogo();

        Animation.fade(background, 0, 1, 0.5, 0.5);
        Animation.offsetY(logo, 0, 1920, 0.5, 2);

        this.getChildren().addAll(background, logo);

        AudioController.playNow("assets/end.wav", () -> {
            StageManager.quit();
        });

        StageManager.hideSpinner();
    }

}