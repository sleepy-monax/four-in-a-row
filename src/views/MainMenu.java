package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainMenu extends HBox {
    public MainMenu()
    {
        this.setAlignment(Pos.CENTER);
        this.setId("background");



        Pane logo = new Pane();
        logo.setId("logo");


        Pane singleplayerButton = Widgets.makeBigButton("Singleplayer");
        singleplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane multiplayerButton = Widgets.makeBigButton("Multiplayer");
        multiplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane orbScores = Widgets.makeOrbButton();
        Pane orbSettings= Widgets.makeOrbButton();
        Pane orbQuit= Widgets.makeOrbButton();

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
