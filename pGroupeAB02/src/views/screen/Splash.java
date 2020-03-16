package views.screen;

import utils.AudioManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import utils.StageManager;
import utils.Animations;
import views.View;
import utils.Widgets;
import views.menu.Main;

public class Splash extends View {
    public Splash() {
        Button button = Widgets.makeButton("Press any key to start...");
        StackPane.setAlignment(button, Pos.BOTTOM_CENTER);
        StackPane.setMargin(button, new Insets(96));
        button.setTranslateY(300);

        Label label = new Label("Groupe AB02\nL. De Laet, C. Lepine, N. Van Bossuyt\n\nRelease 1");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setTextFill(new Color(1, 1, 1, 0.5));
        label.setTranslateY(250);
        StackPane.setAlignment(label, Pos.BOTTOM_CENTER);

        Pane black = new Pane();
        black.setStyle("-fx-background-color: black");

        Parent logo = Widgets.makeLogo();
        logo.setOpacity(0);

        Animations.fade(black, 1, 0, 0.25, 2);
        Animations.fade(logo, 0, 1, 0.25, 2);
        Animations.scale(logo, 4, 1, 0.25, 2, () -> StageManager.background().showSpinner());
        Animations.offsetY(button, 300, -50, 0.25, 3);
        Animations.offsetY(label, 300, -50, 0.30, 3);

        this.setOnKeyTyped(keyEvent -> {
            goToMainMenu();
        });

        button.setOnAction(actionEvent -> {
            goToMainMenu();
        });

        this.getChildren().addAll(black, logo, button, label);
    }

    private void goToMainMenu() {
        StageManager.background().showSpinner();
        StageManager.switchView(new Main());

        AudioManager.playNow("assets/transition.wav", () -> {
            AudioManager.playLoopNow("assets/loop2.wav");
        });
    }
}
