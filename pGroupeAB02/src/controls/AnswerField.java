package controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import models.Game;
import views.Widgets;

public class AnswerField extends HBox {
    public AnswerField(Game game) {
        TextField answer = Widgets.makeTextField("");
        StackPane.setAlignment(answer, Pos.CENTER);

        StackPane answerContainer = new StackPane(answer);
        answerContainer.setPadding(new Insets(0, 0, 0, 14));
        HBox.setHgrow(answerContainer, Priority.ALWAYS);

        Pane buzzer = Widgets.makeBuzzer();
        buzzer.setOnMouseClicked(event -> {
            game.answer(answer.getText());
        });

        setMaxHeight(48);
        getChildren().addAll(answerContainer, buzzer);
    }
}