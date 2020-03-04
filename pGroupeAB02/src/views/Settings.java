package views;

import controller.AudioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.StageManager;

public class Settings extends View {

    public Settings(){
        Pane orbEffect = Widgets.makeOrbButton("assets/music-on.png");
        orbEffect.setPadding(new Insets(0, 72, 0, 72));

        Pane orbMusic = Widgets.makeOrbButton("assets/effect-on.png");
        orbMusic.setPadding(new Insets(0, 72, 0, 72));

        HBox orbContainer = new HBox(16, orbEffect, orbMusic);
        orbContainer.setAlignment(Pos.CENTER);
        orbContainer.setPrefHeight(48);
        orbContainer.setMaxHeight(48);
        StackPane.setAlignment(orbContainer, Pos.CENTER);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(32));

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);
        menuContainer.getChildren().addAll(Widgets.makeTitle("Settings"),orbContainer);
        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> StageManager.goToMainMenu());
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(menuContainer, backButton);

        orbMusic.setOnMouseClicked(MouseEvent ->  AudioController.shutdown());
    }
}
