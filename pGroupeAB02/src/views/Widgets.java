package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public final class Widgets {
    private Widgets(){}

    public static Button makeButton(String text)
    {
        Button button = new Button(text);

        button.getStyleClass().add("button");

        return button;
    }

    public static Pane makeBigButton(String icon, String text)
    {
        AnchorPane button = new AnchorPane();

        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("big-button");

        AnchorPane.setBottomAnchor(label, 12.0);
        AnchorPane.setTopAnchor(label, 12.0);
        AnchorPane.setLeftAnchor(label, 36.0);
        AnchorPane.setRightAnchor(label, 12.0);

        StackPane orb = new StackPane();
        orb.getStyleClass().add("big-button-orb");

        AnchorPane.setBottomAnchor(orb, 0.0);
        AnchorPane.setTopAnchor(orb, 0.0);
        AnchorPane.setLeftAnchor(orb, 0.0);

        ImageView image = new ImageView(icon);
        orb.getChildren().add(image);
        StackPane.setAlignment(image, Pos.CENTER);

        button.getChildren().addAll(label, orb);

        return button;
    }

    public static Pane makeOrbButton(String icon)
    {
        StackPane button = new StackPane();

        button.getStyleClass().add("orb-button");

        ImageView image = new ImageView(icon);
        button.getChildren().add(image);
        StackPane.setAlignment(image, Pos.CENTER);

        return button;
    }

    public  static TextField makeTextField(String text){
        TextField field = new TextField(text);

        field.setAlignment(Pos.CENTER);
        field.getStyleClass().add("textfield");

        return field;
    }
}
