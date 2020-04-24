package views.dialogs;

import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import views.TextStyle;

import static views.Layout.*;
import static views.Widget.*;

public class YesNoDialog extends Dialog<YesNo> {
    public YesNoDialog(String title, String message) {
        super();

        Region buttonsContainer = horizontal(
            16,
            Pos.BOTTOM_RIGHT,
            button("Yes", e -> resume(YesNo.YES)),
            button("No", e -> resume(YesNo.NO))
        );

        Region container = vertical(8,
            text(title, TextStyle.SUBTITLE, Color.BLACK),
            fillWith(horizontallyCentered(text(message, TextStyle.BODY, Color.BLACK))),
            buttonsContainer
        );

        this.getChildren().add(container);
    }
}
