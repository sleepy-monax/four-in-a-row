package views;

import javafx.scene.control.Button;

public final class Widgets {
    private Widgets(){}

    public static Button makeButton(String text)
    {
        Button button = new Button(text);

        button.getStyleClass().add("button");

        return button;
    }
}
