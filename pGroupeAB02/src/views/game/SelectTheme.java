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
import utils.StageManager;
import views.Layout;
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

            themesList.getChildren().add(Layout.width(360, themeButton));
        }

        Button backButton = Widget.button("Go back", event -> game.shutdown());
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(Widget.text("Select a theme", TextStyle.TITLE), themesList, backButton);
    }

    @Override
    public void onAttach() {
        AudioManager.playLoopNow("assets/musics/loop3.wav");
        StageManager.background().hideSpinnerInstant();
    }

    @Override
    public void onSwitchOut() {
        AudioManager.playLoopWithTransition("assets/musics/loop2.wav", "assets/musics/transition.wav");
        StageManager.background().showSpinner();
    }
}
