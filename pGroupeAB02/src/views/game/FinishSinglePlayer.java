package views.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import models.Game;
import utils.AudioManager;
import views.Layout;
import views.TextStyle;
import views.View;
import views.Widget;

public class FinishSinglePlayer extends View {

    public FinishSinglePlayer(Game game) {
        this.setPadding(new Insets(32));

        Button goHome = Widget.button("Go home", event -> game.quit());
        StackPane.setAlignment(goHome, Pos.BOTTOM_LEFT);
        this.setAlignment(Pos.CENTER);

        Region panel = Widget
                .panel(Layout.vertical(16, Widget.text("Hey " + game.getPlayer(0).getName() + "!", TextStyle.SUBTITLE),
                        Widget.text("- Your score is: " + game.getPlayer(0).getScore(), TextStyle.LABEL),
                        Widget.text("- Your max level is: " + game.getPlayer(0).getLevelMax(), TextStyle.LABEL)));

        StackPane.setAlignment(panel, Pos.CENTER);

        this.getChildren().addAll(Widget.text("Game Finished!", TextStyle.TITLE),
                Layout.verticallyCentered(Layout.width(512, panel)), goHome);
    }

    @Override
    public void onSwitchOut() {
        AudioManager.playLoopWithTransition("assets/musics/loop2.wav", "assets/musics/transition.wav");
    }
}
