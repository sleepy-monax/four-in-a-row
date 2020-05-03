package views.game;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import models.Game;
import models.Player;
import models.scores.Score;
import models.scores.ListScore;
import utils.AudioManager;
import views.TextStyle;
import views.View;
import views.widgets.ActualScore;
import views.widgets.MaxLevel;

import static views.Layout.*;
import static views.Widget.*;

public class Finish extends View {

    public Finish(Game game) {
        super(true);

        Pane playerList = vertical(8);

        for (Player player : game.getPlayers()) {
            Region playerPane = panel(
                horizontal(
                    8,
                    verticallyCentered(text(player.getName(), TextStyle.SUBTITLE)),
                    fillWith(verticallyCentered(new ActualScore(player.getScore()))),
                    new MaxLevel(player.getLevelMax())
                )
            );

            playerList.getChildren().add(playerPane);
        }

        Region goHome = backButton(event -> game.shutdown());

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(text("Game Finished!", TextStyle.TITLE),
                verticallyCentered(width(512, playerList)), goHome);

        ListScore.get().addScore(new Score(game.getPlayer(0).getName(), game.getPlayer(0).getScore()));
        ListScore.saveScore();
    }

    @Override
    public void onSwitchOut() {
        AudioManager.playLoopWithTransition("assets/musics/loop2.wav", "assets/musics/transition.wav");
    }
}
