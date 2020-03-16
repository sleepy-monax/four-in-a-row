package views.widgets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Countdown extends Label {
    public Countdown() {
        this.getStyleClass().add("countdown");
        this.setAlignment(Pos.CENTER);
    }

    public void update(int value) {
        setText(value + "'");
    }
}