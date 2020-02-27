package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.MasterGame;
import utils.Main;
import utils.StageManager;

public class MainMenu extends StackPane {
    public MainMenu() {
        Pane singleplayerButton = Widgets.makeBigButton("assets/singleplayer.png", "Singleplayer");
        singleplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane joinMultiplayerButton = Widgets.makeBigButton("assets/multiplayer.png", "Join Multiplayer");
        joinMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane hostMultiplayerButton = Widgets.makeBigButton("assets/multiplayer.png", "Host Multiplayer");
        hostMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));

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
        this.setId("background");
        this.getChildren().add(menuContainer);

        joinMultiplayerButton.setOnMouseClicked(mouseEvent -> StageManager.switchScene(new JoinMultiplayer()));
        hostMultiplayerButton.setOnMouseClicked(
                mouseEvent -> StageManager.switchScene(new PendingGameView(new MasterGame(null, 1234))));

        orbQuit.setOnMouseClicked(mouseEvent -> StageManager.quit());
    }
}
