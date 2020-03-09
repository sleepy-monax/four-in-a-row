package dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import views.Widgets;

public class YesNoDialog extends Dialog<YesNo> {
    public YesNoDialog(String title, String message) {
        super();

        Label titleLabel = new Label(title);
        StackPane.setAlignment(titleLabel, Pos.TOP_LEFT);

        Label messageLabel = new Label(message);

        Button yesButton = Widgets.makeButton("Yes");
        yesButton.setOnAction(e -> resume(YesNo.YES));

        Button noButton = Widgets.makeButton("No");
        noButton.setOnAction(e -> resume(YesNo.NO));

        HBox buttonsContainer = new HBox(16, yesButton, noButton);
        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonsContainer.setMaxHeight(32);

        StackPane.setAlignment(buttonsContainer, Pos.BOTTOM_RIGHT);

        this.getChildren().addAll(titleLabel, messageLabel, buttonsContainer);
    }
}
