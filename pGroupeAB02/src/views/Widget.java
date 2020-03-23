package views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import utils.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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

        button.setOnMousePressed(event -> AudioManager.playEffect("assets/click.wav"));
        button.setOnMouseClicked(onClick);

        return button;
    }

    public static Node buttonWithIcon(Icon icon, String text, EventHandler<? super MouseEvent> onClick) {
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

        button.setOnMousePressed(event -> AudioManager.playEffect("assets/click.wav"));
        button.setOnMouseClicked(onClick);

        return button;
    }

    public static Node iconButton(Icon icon, EventHandler<? super MouseEvent> onClick) {
        return iconButton(() -> icon, onClick);
    }

    public static Node iconButton(Getter<Icon> getIcon, EventHandler<? super MouseEvent> onClick) {
        StackPane iconButton = new StackPane();

        iconButton.getStyleClass().addAll("button", "FIR_orb-button");

        ImageView image = new ImageView(getIcon.call().path);
        iconButton.getChildren().add(image);
        StackPane.setAlignment(image, Pos.CENTER);

        iconButton.setOnMousePressed(event -> AudioManager.playEffect("assets/click.wav"));

        iconButton.setOnMouseClicked(event -> {
            onClick.handle(event);
            image.setImage(new Image(getIcon.call().path));
        });

        return iconButton;
    }

    public static TextField textField() {
        TextField field = new TextField();

        field.setAlignment(Pos.CENTER);
        field.getStyleClass().add("FIR_textfield");

        return field;
    }

    public static TextField textField(String text) {
        TextField field = new TextField(text);

        field.setAlignment(Pos.CENTER);
        field.getStyleClass().add("FIR_textfield");

        return field;
    }

    public static TextField textField(Getter<String> getText, Consumer<String> onTextChange) {
        TextField field = new TextField(getText.call());

        field.setAlignment(Pos.CENTER);
        field.getStyleClass().add("FIR_textfield");
        field.textProperty().addListener((observable, oldValue, newValue) -> onTextChange.accept(newValue));

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

    public static Node slider(Getter<Double> getValue, Consumer<Double> onValueChange) {
        Slider slider = new Slider();

        slider.setMin(0);
        slider.setMax(1);
        slider.setValue(getValue.call());

        final int[] lastTick = {(int) (getValue.call() * 10)};
        final long[] lastTickTime = {System.currentTimeMillis()};


        slider.valueProperty().addListener(event -> {
            int tick = (int) (slider.getValue() * 15);
            int tickDown = (int) (Math.ceil(slider.getValue() * 15));
            int tickUp = (int) (Math.floor(slider.getValue() * 15));

            if (tickDown == 0) {
                AudioManager.playEffect(
                        "assets/tick.wav",
                        0.7);

            } else if (tickUp == 15) {
                AudioManager.playEffect(
                        "assets/tick.wav",
                        1);

            } else if (Math.abs(lastTick[0] - tick) >= 1 && lastTickTime[0] + 50 <= System.currentTimeMillis()) {
                lastTick[0] = tick;
                lastTickTime[0] = System.currentTimeMillis();


                AudioManager.playEffect(
                        "assets/tick.wav",
                        slider.getValue() * 0.1 + 0.8);
            }


            onValueChange.accept(slider.getValue());
        });

        return slider;
    }
}
