package views.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import models.Game;
import models.Player;
import views.Layout;
import views.TextStyle;
import views.View;
import views.Widget;

public class OtherPlayerPlaying extends View {
    public OtherPlayerPlaying(Game game, Player player, String what) {
        this.setPadding(new Insets(32));

        Label label = Widget.text(player.getName() + " is " + what, TextStyle.SUBTITLE);

        StackPane.setAlignment(label, Pos.CENTER);

        this.getChildren().addAll(Widget.text("Another player is playing", TextStyle.TITLE),
                Layout.verticallyCentered(label));
    }
}