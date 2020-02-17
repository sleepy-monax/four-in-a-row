package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.Main;

public class MainMenu extends HBox {
    public MainMenu()
    {
        this.setAlignment(Pos.CENTER);
        this.setId("background");

        Pane logo = new Pane();
        logo.setId("logo");

        Pane singleplayerButton = Widgets.makeBigButton("assets/singleplayer.png", "Singleplayer" );
        singleplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane multiplayerButton = Widgets.makeBigButton( "assets/multiplayer.png","Multiplayer");
        multiplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane orbScores = Widgets.makeOrbButton("assets/score.png");
        Pane orbSettings= Widgets.makeOrbButton("assets/settings.png");

        Pane orbQuit= Widgets.makeOrbButton("assets/close.png");
        orbQuit.setOnMouseClicked(mouseEvent -> Main.quit());

        HBox orbContainer = new HBox(16, orbScores, orbSettings, orbQuit);
        orbContainer.setAlignment(Pos.CENTER);
        orbContainer.setPrefHeight(48);

        VBox root = new VBox(16);
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(logo, singleplayerButton, multiplayerButton, orbContainer);

        setFillHeight(true);
        getChildren().add(root);
    }
}
