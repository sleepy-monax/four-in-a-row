package views.game;

import javafx.scene.Node;
import utils.AudioManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Game;
import utils.Icon;
import utils.StageManager;
import views.TextStyle;
import views.View;

import static views.Layout.*;
import static views.Widget.*;

public class SelectTheme extends View {

    public SelectTheme(Game game, String[] themes) {
        super(true);

        VBox themesList = new VBox(16);

        themesList.setAlignment(Pos.CENTER);
        themesList.setMaxWidth(512);

        for (String theme : themes) {
            Node themeButton = buttonWithIcon(Icon.STAR, theme, event -> {
                game.selectTheme(theme);
            });

            themesList.getChildren().add(width(360, themeButton));
        }

        Pane themeButton = buttonWithIcon(Icon.SHUFFLE, "Mystery theme", event -> {
            game.selectMisteryTheme();
        });

        themesList.getChildren().addAll(spacer(16), width(360, themeButton));

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(
            text("Select a theme", TextStyle.TITLE), 
            themesList, 
            backButton(actionEvent -> game.shutdown())
        );
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
