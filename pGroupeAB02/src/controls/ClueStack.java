package controls;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import views.Animation;

public class ClueStack extends StackPane {
    private final VBox backClues;
    private final VBox frontClues;

    public ClueStack() {
        backClues = new VBox(16);
        frontClues = new VBox(16);

        setPrefHeight(128 * 3);
        setPrefWidth(512);

        getChildren().addAll(backClues, frontClues);
    }

    public void addClue(String clue) {
        ClueCard card = new ClueCard(clue);

        Animation.offsetY(card, -512, 0, 0.25);

        StackPane.setAlignment(card, Pos.CENTER);

        frontClues.getChildren().add(card);
    }

    public void clearClues() {
        double offset = 0;

        for (Node card : new ArrayList<>(frontClues.getChildren())) {
            frontClues.getChildren().remove(card);
            backClues.getChildren().add(card);

            Animation.scale(card, 1, 0, 0.5, offset, () -> {
                backClues.getChildren().remove(card);
            });

            offset += 0.1;
        }

    }
}
