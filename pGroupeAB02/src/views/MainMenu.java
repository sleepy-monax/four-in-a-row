package views;

import dialogs.YesNo;
import dialogs.YesNoDialog;
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
        Pane singleplayerButton = Widgets.makeBigButton(Icon.PERSON, "Singleplayer");
        singleplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane joinMultiplayerButton = Widgets.makeBigButton(Icon.GROUP_ADD, "Join Multiplayer");
        joinMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane hostMultiplayerButton = Widgets.makeBigButton(Icon.GROUP, "Host Multiplayer");
        hostMultiplayerButton.setPadding(new Insets(0, 72, 0, 72));

        Pane orbEditor = Widgets.makeOrbButton(Icon.EDIT);
        Pane orbScores = Widgets.makeOrbButton(Icon.EMOJI_EVENTS);
        Pane orbSettings = Widgets.makeOrbButton(Icon.SETTINGS);
        Pane orbQuit = Widgets.makeOrbButton(Icon.CLOSE);

        HBox orbContainer = new HBox(16, orbEditor, orbScores, orbSettings, orbQuit);
        orbContainer.setAlignment(Pos.CENTER);
        orbContainer.setPrefHeight(48);
        orbContainer.setMaxHeight(48 + 24);
        orbContainer.setPadding(new Insets(24, 0, 0, 0));
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

        singleplayerButton.setOnMouseClicked(event -> StageManager.switchView(new SelectTheme(null, 3)));
        joinMultiplayerButton.setOnMouseClicked(mouseEvent -> StageManager.switchView(new JoinMultiplayer()));
        hostMultiplayerButton.setOnMouseClicked(mouseEvent -> Multiplayer.host(Multiplayer.DEFAULT_PORT));

        orbEditor.setOnMouseClicked(mouseEvent -> StageManager.switchView((new Editor())));
        orbSettings.setOnMouseClicked(mouseEvent -> StageManager.switchView((new Settings())));
        orbQuit.setOnMouseClicked(mouseEvent -> {
            if (new YesNoDialog("Quit", "Do you want to quit?").show() == YesNo.YES) {

                StageManager.switchView(new EndScreen());
            }
        });
    }
}
