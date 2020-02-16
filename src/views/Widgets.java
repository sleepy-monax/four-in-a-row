package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

public final class Widgets {
    private Widgets(){}

    public static Button makeButton(String text)
    {
        Button button = new Button(text);

        button.getStyleClass().add("button");

        return button;
    }

    public static Parent makeBigButton(String text)
    {
        AnchorPane button = new AnchorPane();
        button.setPadding(new Insets(0, 72, 0, 72));

        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("big-button");
        AnchorPane.setBottomAnchor(label, 12.0);
        AnchorPane.setTopAnchor(label, 12.0);
        AnchorPane.setLeftAnchor(label, 12.0);
        AnchorPane.setRightAnchor(label, 12.0);


        Pane orb = new Pane();
        orb.getStyleClass().add("big-button-orb");
        AnchorPane.setBottomAnchor(orb, 0.0);
        AnchorPane.setTopAnchor(orb, 0.0);
        AnchorPane.setLeftAnchor(orb, 0.0);

        button.getChildren().addAll(label, orb);

        return button;
    }
}
