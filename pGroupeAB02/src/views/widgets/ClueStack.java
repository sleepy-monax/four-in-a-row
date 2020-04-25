package views.widgets;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.Animations;

import java.util.ArrayList;

public class ClueStack extends StackPane {
    private final VBox backClues;
    private final VBox frontClues;

    public ClueStack() {
        backClues = new VBox(16);
        backClues.setMaxWidth(ClueCard.WIDTH);

        frontClues = new VBox(16);
        frontClues.setMaxWidth(ClueCard.WIDTH);

        setPrefHeight(128 * 3);
        setPrefWidth(512);

        getChildren().addAll(backClues, frontClues);
    }

    public void addClue(String clue) {
        ClueCard card = new ClueCard(clue);

        Animations.translateY(-512, 0, 0.25, card);
        StackPane.setAlignment(card, Pos.CENTER);
        frontClues.getChildren().add(card);
    }

    public void clearClues() {
        double offset = 0;

        for (Node card : new ArrayList<>(frontClues.getChildren())) {
            frontClues.getChildren().remove(card);
            backClues.getChildren().add(card);

            Animations.scale(card, 1, 0, 0.5, offset, () -> {
                backClues.getChildren().remove(card);
            });

            offset += 0.1;
        }

    }
}
