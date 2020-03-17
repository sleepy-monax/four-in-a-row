package views.dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import views.Layout;
import views.Widget;

public class YesNoDialog extends Dialog<YesNo> {
    public YesNoDialog(String title, String message) {
        super();

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("dialog-title");

        StackPane.setAlignment(titleLabel, Pos.TOP_LEFT);

        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("dialog-body");
        messageLabel.setTextAlignment(TextAlignment.CENTER);


        Region buttonsContainer = Layout.horizontal(
                16,
                Pos.BOTTOM_RIGHT,
                Widget.button("Yes", e -> resume(YesNo.YES)),
                Widget.button("No", e -> resume(YesNo.NO)));

        StackPane.setAlignment(buttonsContainer, Pos.BOTTOM_RIGHT);

        this.getChildren().addAll(titleLabel, messageLabel, buttonsContainer);
    }
}
