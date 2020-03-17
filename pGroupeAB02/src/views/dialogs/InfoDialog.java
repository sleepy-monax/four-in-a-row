package views.dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import views.Widget;

public class InfoDialog extends Dialog<OkCancel> {
    public InfoDialog(String title, String message) {
        super();

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("dialog-title");
        StackPane.setAlignment(titleLabel, Pos.TOP_LEFT);

        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("dialog-body");
        messageLabel.setTextAlignment(TextAlignment.CENTER);

        Button okButton = Widget.button("Ok", e -> resume(OkCancel.OK));
        StackPane.setAlignment(okButton, Pos.BOTTOM_RIGHT);

        this.getChildren().addAll(titleLabel, messageLabel, okButton);
    }
}