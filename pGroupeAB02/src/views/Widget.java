package views;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import utils.AudioManager;
import utils.Getter;
import utils.Icon;
import utils.StageManager;

public final class Widget {
    private Widget() {
    }

    public static Parent logo() {
        AnchorPane logo = new AnchorPane();
        logo.setId("logo");

        Pane buzzer = Widget.buzzer();

        AnchorPane.setTopAnchor(buzzer, 0.0);
        AnchorPane.setRightAnchor(buzzer, 32.0);

        logo.getChildren().add(buzzer);

        return logo;
    }

    public static Button button(String text, EventHandler<? super MouseEvent> onClick) {
        Button button = new Button(text);

        button.getStyleClass().addAll("button", "FIR_button");
        button.setMinWidth(96);
        button.setAlignment(Pos.CENTER);
        button.setOnMouseClicked(onClick);

        return button;
    }

    public static Pane buttonWithIcon(Icon icon, String text) {
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

        ImageView image = new ImageView(icon.path);
        orb.getChildren().add(image);
        StackPane.setAlignment(image, Pos.CENTER);

        button.getChildren().addAll(label, orb);
        button.setMaxHeight(72);

        return button;
    }

    public static TextField textField(String text) {
        TextField field = new TextField(text);

        field.setAlignment(Pos.CENTER);
        field.getStyleClass().add("FIR_textfield");

        return field;
    }

    public static Label label(String text) {
        Label label = new Label(text);

        label.getStyleClass().add("FIR_label");

        return label;
    }

    public static Pane smallBuzzer() {
        Pane buzzer = new Pane();
        buzzer.setMinSize(96, 96);
        buzzer.setMaxSize(96, 96);

        buzzer.getStyleClass().add("FIR_buzzer");

        AtomicInteger click_count = new AtomicInteger();
        buzzer.setOnMouseClicked(mouseEvent -> {
            click_count.getAndIncrement();

            if (click_count.get() == 10) {
                StageManager.setTitle("Ah oui oui oui oui oui");
            } else {
                AudioManager.playEffect("assets/bruh.wav");
            }
        });

        return buzzer;
    }

    public static Pane buzzer() {
        Pane buzzer = new Pane();
        buzzer.setMinSize(128, 128);
        buzzer.setMaxSize(128, 128);

        buzzer.getStyleClass().add("FIR_big-buzzer");

        AtomicInteger click_count = new AtomicInteger();
        buzzer.setOnMouseClicked(mouseEvent -> {
            if (click_count.incrementAndGet() == 1) {
                StageManager.setTitle("Ah oui oui oui oui oui");
                AudioManager.playEffect("assets/ouai.wav");

            } else if (click_count.get() > 1) {
                AudioManager.playEffect("assets/bruh.wav");
            }
        });

        return buzzer;
    }

    public static Node iconButton(Icon icon, EventHandler<? super MouseEvent> onClick) {
        return iconButton(() -> icon, onClick);
    }

    public static Node iconButton(Getter<Icon> getIcon, EventHandler<? super MouseEvent> onClick)
    {
        StackPane iconButton = new StackPane();

        iconButton.getStyleClass().addAll("button", "FIR_orb-button");

        ImageView image = new ImageView(getIcon.call().path);
        iconButton.getChildren().add(image);
        StackPane.setAlignment(image, Pos.CENTER);

        iconButton.setOnMouseClicked(event -> {
            onClick.handle(event);
            image.setImage(new Image(getIcon.call().path));
        });

        return iconButton;
    }

    public static Node slider(double min, double max, Getter<Double> getValue, Consumer<Double> onValueChange)
    {
        Slider slider = new Slider();

        slider.setMin(min);
        slider.setMax(max);
        slider.setValue(getValue.call());
        slider.valueProperty().addListener(event -> {
            onValueChange.accept(slider.getValue());
        });

        return slider;
    }
}
