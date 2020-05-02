package views.widgets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class ActualScore extends Label {

    public ActualScore(int value) {
        this.getStyleClass().add("actualScore");
        this.setAlignment(Pos.CENTER);
        this.setTextAlignment(TextAlignment.CENTER);
        setText("" + value);
    }

    public void update(int value) {
        setText("" + value);
    }
}
