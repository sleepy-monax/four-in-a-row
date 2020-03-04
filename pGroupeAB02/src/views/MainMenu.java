package views;

import controller.AudioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import multiplayer.Multiplayer;
import utils.StageManager;

public class MainMenu extends View {
    public MainMenu() {
        Pane singleplayerButton = Widgets.makeBigButton("assets/singleplayer.png", "Singleplayer");
        singleplayerButton.setPadding(new Insets(0, 72, 0, 72));
        singleplayerButton.setOnMouseClicked(event -> StageManager.switchView(new SelectTheme(null, 3)));

        Pane joinMultiplayerButton = Widgets.makeBigButton("assets/multiplayer.png", "Join Multiplayer");
        joinMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));
        joinMultiplayerButton.setOnMouseClicked(mouseEvent -> StageManager.switchView(new JoinMultiplayer()));

        Pane hostMultiplayerButton = Widgets.makeBigButton("assets/multiplayer.png", "Host Multiplayer");
        hostMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));
        hostMultiplayerButton.setOnMouseClicked(mouseEvent -> Multiplayer.host(Multiplayer.DEFAULT_PORT));

        Pane orbScores = Widgets.makeOrbButton("assets/score.png");
        Pane orbSettings = Widgets.makeOrbButton("assets/settings.png");
        Pane orbQuit = Widgets.makeOrbButton("assets/close.png");

        HBox orbContainer = new HBox(16, orbScores, orbSettings, orbQuit);
        orbContainer.setAlignment(Pos.CENTER);
        orbContainer.setPrefHeight(48);
        orbContainer.setMaxHeight(48);
        StackPane.setAlignment(orbContainer, Pos.BOTTOM_CENTER);

        VBox menuContainer = new VBox(16, Widgets.makeLogo(), singleplayerButton, joinMultiplayerButton,
                hostMultiplayerButton, orbContainer) {
            {
                setAlignment(Pos.CENTER);
                setMaxWidth(512);
            }
        };

        this.setAlignment(Pos.CENTER);
        this.getChildren().add(menuContainer);

        orbQuit.setOnMouseClicked(mouseEvent -> {
            StageManager.switchView(new EndScreen());
            AudioController.playNow("assets/end.wav", () -> {
                StageManager.quit();
            });
        });

        orbSettings.setOnMouseClicked(mouseEvent-> StageManager.switchView((new Settings())));
    }
}
