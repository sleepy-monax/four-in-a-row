package views.game;

import javafx.scene.Node;
import utils.AudioManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Game;
import utils.Icon;
import views.TextStyle;
import views.View;
import views.Widget;

public class SelectTheme extends View {

    public SelectTheme(Game game, String[] themes) {
        this.setPadding(new Insets(32));

        VBox themesList = new VBox(16);

        themesList.setAlignment(Pos.CENTER);
        themesList.setMaxWidth(512);

        for (String theme : themes) {
            Node themeButton = Widget.buttonWithIcon(Icon.STAR, theme, event -> {
                game.selectTheme(theme);
            });

            themesList.getChildren().add(themeButton);
        }

        Button backButton = Widget.button("Go back", event -> game.finish());
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(Widget.text("Select a theme", TextStyle.TITLE), themesList, backButton);
    }

    @Override
    public void onSwitchIn() {
        AudioManager.playLoopNow("assets/theme.wav");
    }

    @Override
    public void onSwitchOut() {
        AudioManager.playNow("assets/transition.wav", () -> {
            AudioManager.playLoopNow("assets/loop2.wav");
        });
    }
}
