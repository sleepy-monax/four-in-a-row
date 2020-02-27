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
import utils.Main;
import utils.StageManager;

import java.util.concurrent.atomic.AtomicInteger;

public final class Widgets {
    private Widgets() {
    }

    public static Parent makeLogo() {
        AnchorPane logo = new AnchorPane();
        logo.setId("logo");

        Pane buzzer = Widgets.makeBigBuzzer();

        AnchorPane.setTopAnchor(buzzer, 0.0);
        AnchorPane.setRightAnchor(buzzer, 32.0);

        logo.getChildren().add(buzzer);

        return logo;
    }

    public static Button makeButton(String text) {
        Button button = new Button(text);

        button.getStyleClass().addAll("button", "FIR_button");

        return button;
    }

    public static Pane makeBigButton(String icon, String text) {
        AnchorPane button = new AnchorPane();

        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().addAll("button", "FIR_big-button");

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
        button.setMaxHeight(72);

        return button;
    }

    public static Pane makeOrbButton(String icon) {
        StackPane button = new StackPane();

        button.getStyleClass().addAll("button", "FIR_orb-button");

        ImageView image = new ImageView(icon);
        button.getChildren().add(image);
        StackPane.setAlignment(image, Pos.CENTER);

        return button;
    }

    public static TextField makeTextField(String text) {
        TextField field = new TextField(text);

        field.setAlignment(Pos.CENTER);
        field.getStyleClass().add("FIR_textfield");

        return field;
    }

    public static Label makeLabel(String text) {
        Label label = new Label(text);

        label.getStyleClass().add("FIR_label");

        return label;
    }

    public static Parent makeTitle(String text) {
        Label label = new Label(text);

        label.getStyleClass().add("FIR_title");

        return label;
    }

    public static Pane makeBuzzer() {
        Pane buzzer = new Pane();
        buzzer.setMinSize(96, 96);
        buzzer.setMaxSize(96, 96);

        buzzer.getStyleClass().add("FIR_buzzer");

        AtomicInteger click_count = new AtomicInteger();
        buzzer.setOnMouseClicked(mouseEvent -> {
            click_count.getAndIncrement();

            if (click_count.get() == 10) {
                StageManager.setTitle("Ah oui oui oui oui oui");
            }
        });

        return buzzer;
    }

    public static Pane makeBigBuzzer() {
        Pane buzzer = new Pane();
        buzzer.setMinSize(128, 128);
        buzzer.setMaxSize(128, 128);

        buzzer.getStyleClass().add("FIR_big-buzzer");

        AtomicInteger click_count = new AtomicInteger();
        buzzer.setOnMouseClicked(mouseEvent -> {
            click_count.getAndIncrement();

            if (click_count.get() == 10) {
                StageManager.setTitle("Ah oui oui oui oui oui");
            }
        });

        return buzzer;
    }
}
