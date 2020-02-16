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

    public static Pane makeBigButton(String text)
    {
        AnchorPane button = new AnchorPane();

        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("big-button");
        AnchorPane.setBottomAnchor(label, 12.0);
        AnchorPane.setTopAnchor(label, 12.0);
        AnchorPane.setLeftAnchor(label, 36.0);
        AnchorPane.setRightAnchor(label, 12.0);


        Pane orb = new Pane();
        orb.getStyleClass().add("big-button-orb");
        AnchorPane.setBottomAnchor(orb, 0.0);
        AnchorPane.setTopAnchor(orb, 0.0);
        AnchorPane.setLeftAnchor(orb, 0.0);

        button.getChildren().addAll(label, orb);

        return button;
    }

    public static Pane makeOrbButton()
    {
        Pane button = new Pane();

        button.getStyleClass().add("orb-button");

        return button;
    }
}
