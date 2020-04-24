package views.dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import models.Question;
import views.TextStyle;

import static views.Layout.*;
import static views.Widget.*;


public class EditQuestion extends Dialog<OkCancel> {
    public EditQuestion(Question question) {
        super();

        Region buttonsContainer = horizontal(
            16,
            Pos.BOTTOM_RIGHT,
            button("Cancel", e -> resume(OkCancel.CANCEL)),
            button("Ok", e -> resume(OkCancel.OK))
        );

        TextField[] clues = new TextField[question.getClues().size()];

        for (int i = 0; i < question.getClues().size(); i++) {
            final int clueIndex = i;
            clues[i] = textField(() -> question.getClues().get(clueIndex), value -> question.getClues().set(clueIndex, value));
        }

        Region container = vertical(8,
            text("Edit a question", TextStyle.SUBTITLE, Color.BLACK),
            text("Author", TextStyle.LABEL, Color.BLACK),
            textField(() -> question.getAuthor(), value -> question.setAuthor(value)),
            text("Theme", TextStyle.LABEL, Color.BLACK),
            textField(() -> question.getTheme(), value -> question.setTheme(value)),
            text("Answer", TextStyle.LABEL, Color.BLACK),
            textField(() -> question.getAnswer(), value -> question.setAnswer(value)),
            text("Clues", TextStyle.LABEL, Color.BLACK),
            vertical(4, clues),
            buttonsContainer
        );

        this.setMaxHeight(USE_PREF_SIZE);

        this.getChildren().add(container);
    }
}