package controls;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import views.Animation;

public class ClueStack extends VBox {
    public ClueStack() {
        super(16);
        setPrefHeight(128 * 3);
        setPrefWidth(512);
    }

    public void addClue(String clue) {
        ClueCard card = new ClueCard(clue);

        Animation.offsetY(card, -512, 0, 0.25);

        StackPane.setAlignment(card, Pos.CENTER);

        getChildren().add(new StackPane(card));
    }
}
