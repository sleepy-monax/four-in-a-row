package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.Main;

public class MainMenu extends StackPane {
    public MainMenu() {
        Pane singleplayerButton = Widgets.makeBigButton("assets/singleplayer.png", "Singleplayer");
        singleplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane multiplayerButton = Widgets.makeBigButton("assets/multiplayer.png", "Multiplayer");
        multiplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane orbScores = Widgets.makeOrbButton("assets/score.png");
        Pane orbSettings = Widgets.makeOrbButton("assets/settings.png");
        Pane orbQuit = Widgets.makeOrbButton("assets/close.png");

        HBox orbContainer = new HBox(16, orbScores, orbSettings, orbQuit);
        orbContainer.setAlignment(Pos.CENTER);
        orbContainer.setPrefHeight(48);
        orbContainer.setMaxHeight(48);
        StackPane.setAlignment(orbContainer, Pos.BOTTOM_CENTER);

        VBox menuContainer = new VBox(
                16,
                Widgets.makeLogo(),
                singleplayerButton,
                multiplayerButton,
                orbContainer
        ) {{
            setAlignment(Pos.CENTER);
            setMaxWidth(512);
        }};

        this.setAlignment(Pos.CENTER);
        this.setId("background");
        this.getChildren().add(menuContainer);

        multiplayerButton.setOnMouseClicked(mouseEvent -> Main.switchScene(new MultiplayerSelect()));
        orbQuit.setOnMouseClicked(mouseEvent -> Main.quit());
    }
}
