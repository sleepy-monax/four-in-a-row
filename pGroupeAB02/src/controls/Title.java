package controls;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Title extends Label {
    public Title(String text) {
        super(text);

        StackPane.setAlignment(this, Pos.TOP_CENTER);
        getStyleClass().add("FIR_title");
    }
}