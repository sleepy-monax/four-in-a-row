package views.screen;

import utils.AudioManager;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import utils.StageManager;
import utils.Animations;
import views.View;
import views.Widget;

public class End extends View {

    public End() {

        Pane background = new Pane();
        background.setStyle("-fx-background-color: black;");
        background.setOpacity(0);

        Parent logo = Widget.logo();

        Animations.fade(background, 0, 1, 0.5, 0.5);
        Animations.translateY(logo, 0, 1920, 0.5, 2);

        this.getChildren().addAll(background, logo);

        AudioManager.playNow("assets/musics/end.wav", StageManager::quit);

        StageManager.background().hideSpinner();
    }

}