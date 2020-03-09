package views;

import controller.AudioController;
import controls.Title;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.StageManager;

public class Settings extends View {

    public Settings() {
        this.setPadding(new Insets(32));

        Pane orbEffect = Widgets.makeOrbButton(Icon.MUSIC_OFF);
        orbEffect.setPadding(new Insets(0, 72, 0, 72));

        Pane orbMusic = Widgets.makeOrbButton(Icon.VOLUME_OFF);
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
        menuContainer.getChildren().add(orbContainer);

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> StageManager.switchView(new MainMenu()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(new Title("Settings"), menuContainer, backButton);

        orbMusic.setOnMouseClicked(MouseEvent -> AudioController.shutdown());
    }
}
