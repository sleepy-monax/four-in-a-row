package views.widgets;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import models.Player;
import utils.Animations;

public class MaxLevel extends HBox {

    private Pane[] levels;

    public MaxLevel(int value){
        super(8);
        this.getStyleClass().add("maxLevel");
        this.setAlignment(Pos.CENTER);

        levels = new Pane[Player.MAX_LEVEl];
        for (int i = 0; i < Player.MAX_LEVEl; i++) {
            levels[i] = new Pane();
            levels[i].setMinSize(48, 48);
            levels[i].setMaxSize(48, 48);

            levels[i].setId("level-black");
        }

        update(value);

        this.getChildren().addAll(levels);
    }

    public void update(int value) {
        for (int i = 1; i <= value; i++) {
            if (!levels[i - 1].getId().equals("level-gold"))
            {
                levels[i - 1].setId("level-gold");
                Animations.scale(levels[i - 1], 1, 1.25, 0.1, 0);
                Animations.scale(levels[i - 1], 1.25, 1, 0.1, 0.1);
            }
        }

        for (int i = value + 1; i <= Player.MAX_LEVEl; i++) {
            levels[i - 1].setId("level-black");
        }
    }

}
