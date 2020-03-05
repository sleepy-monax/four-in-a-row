package views;

import controller.AudioController;
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

public class SplashScreen extends View {
    private void goToMainMenu() {
        StageManager.showSpinner();
        StageManager.switchView(new MainMenu());

        AudioController.playNow("assets/transition.wav", () -> {
            AudioController.playLoopNow("assets/loop2.wav");
        });
    }

    public SplashScreen() {
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

        Animation.fade(black, 1, 0, 0.25, 2);
        Animation.fade(logo, 0, 1, 0.25, 2);
        Animation.scale(logo, 4, 1, 0.25, 2, () -> StageManager.showSpinner());
        Animation.offsetY(button, 300, -50, 0.25, 3);
        Animation.offsetY(label, 300, -50, 0.30, 3);

        this.setOnKeyTyped(keyEvent -> {
            goToMainMenu();
        });

        button.setOnAction(actionEvent -> {
            goToMainMenu();
        });

        this.getChildren().addAll(black, logo, button, label);
    }
}
