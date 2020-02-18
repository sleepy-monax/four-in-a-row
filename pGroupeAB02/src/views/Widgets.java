package views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public final class Widgets {
    private Widgets(){}

    public static Button makeButton(String text)
    {
        Button button = new Button(text);

        button.getStyleClass().add("FIR_button");

        return button;
    }

    public static Pane makeBigButton(String icon, String text)
    {
        AnchorPane button = new AnchorPane();

        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("FIR_big-button");

        AnchorPane.setBottomAnchor(label, 12.0);
        AnchorPane.setTopAnchor(label, 12.0);
        AnchorPane.setLeftAnchor(label, 36.0);
        AnchorPane.setRightAnchor(label, 12.0);

        StackPane orb = new StackPane();
        orb.getStyleClass().add("FIR_big-button-orb");

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

        button.getStyleClass().add("FIR_orb-button");

        ImageView image = new ImageView(icon);
        button.getChildren().add(image);
        StackPane.setAlignment(image, Pos.CENTER);

        return button;
    }

    public  static TextField makeTextField(String text){
        TextField field = new TextField(text);

        field.setAlignment(Pos.CENTER);
        field.getStyleClass().add("FIR_textfield");

        return field;
    }

    public  static  Label makeLabel(String text)
    {
        Label label = new Label(text);

        label.getStyleClass().add("FIR_label");

        return label;
    }

    public static Parent makeTitle(String text)
    {
        Label label = new Label(text);

        label.getStyleClass().add("FIR_title");

        VBox title_container = new VBox(
                Widgets.makeBuzzer(),
                label);

        title_container.setAlignment(Pos.CENTER);

        return title_container;
    }

    public static Pane makeBuzzer()
    {
        Pane buzzer = new Pane();
        buzzer.setMinSize(96, 96);
        buzzer.setMaxSize(96, 96);

        buzzer.getStyleClass().add("FIR_buzzer");

        return buzzer;
    }
}
