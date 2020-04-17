package views.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.multiplayer.Multiplayer;
import models.singleplayer.SinglePlayer;
import utils.Icon;
import utils.StageManager;
import views.Layout;
import views.View;
import views.Widget;
import views.dialogs.YesNo;
import views.dialogs.YesNoDialog;
import views.game.Join;
import views.screen.End;

public class Main extends View {
    public Main() {
        Node singleplayerButton = Widget.buttonWithIcon(Icon.PERSON, "Singleplayer", event -> SinglePlayer.play());
        Node joinMultiplayerButton = Widget.buttonWithIcon(Icon.GROUP_ADD, "Join Multiplayer",
                mouseEvent -> StageManager.switchView(new Join()));
        Node hostMultiplayerButton = Widget.buttonWithIcon(Icon.GROUP, "Host Multiplayer",
                mouseEvent -> Multiplayer.host(Multiplayer.DEFAULT_PORT));

        Node orbEditor = Widget.iconButton(Icon.EDIT, event -> StageManager.switchView((new AdminConnect())));

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

        VBox menuContainer = new VBox(16, Widget.logo(),
                Layout.horizontallyCentered(Layout.width(360,
                        Layout.vertical(16, singleplayerButton, joinMultiplayerButton, hostMultiplayerButton))),
                orbContainer) {
            {
                setAlignment(Pos.CENTER);
                setMaxWidth(512);
            }
        };

        this.setAlignment(Pos.CENTER);
        this.getChildren().add(menuContainer);
    }

}
