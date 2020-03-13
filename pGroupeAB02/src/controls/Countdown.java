package controls;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Countdown extends Label {
    public Countdown() {
        this.getStyleClass().add("countdown");
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: red");
    }

    public void update(int value) {
        setText(value + "'");
    }
}