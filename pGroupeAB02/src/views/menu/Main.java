package views.menu;

import javafx.scene.Node;
import views.dialogs.YesNo;
import views.dialogs.YesNoDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Deck;
import models.multiplayer.Multiplayer;
import models.singleplayer.SinglePlayer;
import utils.Icon;
import utils.Serialization;
import utils.StageManager;
import views.Widget;
import views.*;
import views.game.Join;
import views.screen.End;

public class Main extends View {
    public Main() {
        Pane singleplayerButton = Widget.buttonWithIcon(Icon.PERSON, "Singleplayer");
        singleplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane joinMultiplayerButton = Widget.buttonWithIcon(Icon.GROUP_ADD, "Join Multiplayer");
        joinMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane hostMultiplayerButton = Widget.buttonWithIcon(Icon.GROUP, "Host Multiplayer");
        hostMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Node orbEditor = Widget.iconButton(Icon.EDIT, event -> StageManager
                .switchView((new Editor(Serialization.readFromJsonFile("data/question.json", Deck.class)))));

        Node orbScores = Widget.iconButton(Icon.EMOJI_EVENTS, mouseEvent -> StageManager.switchView((new Score())));

        Node orbSettings = Widget.iconButton(Icon.SETTINGS, event -> StageManager.switchView((new Settings())));

        Node orbQuit = Widget.iconButton(Icon.CLOSE, event -> {
            if (new YesNoDialog("Quit the game", "Are you sure you want to quit?").show() == YesNo.YES) {
                StageManager.switchView(new End());
            }
        });

        HBox orbContainer = new HBox(16, orbEditor, orbScores, orbSettings, orbQuit);
        orbContainer.setAlignment(Pos.CENTER);
        orbContainer.setPrefHeight(48);
        orbContainer.setMaxHeight(48 + 24);
        orbContainer.setPadding(new Insets(24, 0, 0, 0));
        StackPane.setAlignment(orbContainer, Pos.BOTTOM_CENTER);

        VBox menuContainer = new VBox(16, Widget.logo(), singleplayerButton, joinMultiplayerButton,
                hostMultiplayerButton, orbContainer) {
            {
                setAlignment(Pos.CENTER);
                setMaxWidth(512);
            }
        };

        this.setAlignment(Pos.CENTER);
        this.getChildren().add(menuContainer);

        singleplayerButton.setOnMouseClicked(event -> SinglePlayer.play());
        joinMultiplayerButton.setOnMouseClicked(mouseEvent -> StageManager.switchView(new Join()));
        hostMultiplayerButton.setOnMouseClicked(mouseEvent -> Multiplayer.host(Multiplayer.DEFAULT_PORT));
    }
}
