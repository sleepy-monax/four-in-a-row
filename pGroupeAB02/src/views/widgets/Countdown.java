package views.widgets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import models.states.Round;

public class Countdown extends Label {

    public Countdown() {
        this.getStyleClass().add("countdown");
        this.setAlignment(Pos.CENTER);
        update((int)Round.ROUND_TIME);
    }

    public void update(int value) {
        setText(value + "'");
    }
}