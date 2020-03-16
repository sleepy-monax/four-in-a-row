package utils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class Layout {
    private Layout(){}

    public static Parent verticalyCentered(Node node)
    {
        VBox vbox = new VBox(node);

        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }
}
