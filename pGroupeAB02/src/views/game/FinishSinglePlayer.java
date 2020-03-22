package views.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import models.Game;
import views.Layout;
import views.View;
import views.Widget;
import views.widgets.Title;

public class FinishSinglePlayer extends View {

    public FinishSinglePlayer(Game game ){
        this.setPadding(new Insets(32));

        Button goHome = Widget.button("Go home", event -> game.finish());
        StackPane.setAlignment(goHome, Pos.BOTTOM_LEFT);
        this.setAlignment(Pos.CENTER);

        Title title = new Title("Hey "+game.getPlayer(0).getName()+" \n- Your score is: "+game.getPlayer(0).getScore()+" \n- Your max level is: "+game.getPlayer(0).getLevelMax());

        this.getChildren().addAll(Layout.verticallyCentered(title) ,goHome);
    }
}
