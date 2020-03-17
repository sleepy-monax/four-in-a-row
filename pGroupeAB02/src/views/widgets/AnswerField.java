package views.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import views.Widget;

import java.util.function.Consumer;

public class AnswerField extends HBox {
    private final TextField answer;
    private Consumer<String> onAnswerCallback;

    public AnswerField() {
        answer = Widget.textField("");
        answer.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                onAnswerCallback.accept(answer.getText());
            }
        });

        StackPane.setAlignment(answer, Pos.CENTER);

        Pane buzzer = Widget.smallBuzzer();
        buzzer.setOnMouseClicked(event -> {
            onAnswerCallback.accept(answer.getText());
        });

        StackPane answerContainer = new StackPane(answer);
        answerContainer.setPadding(new Insets(0, 0, 0, 14));
        HBox.setHgrow(answerContainer, Priority.ALWAYS);

        setMaxHeight(48);
        getChildren().addAll(answerContainer, buzzer);
    }

    public void setOnAnswer(Consumer<String> onAnswerCallback) {
        this.onAnswerCallback = onAnswerCallback;
    }

    public void clear() {
        answer.clear();
    }
}