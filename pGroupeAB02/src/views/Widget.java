package views;

import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

        button.setOnMousePressed(event -> AudioManager.playEffect("assets/effects/click.wav"));
        button.setOnMouseClicked(event -> {
            Animations.scale(button, 1.1, 1.0, 0.1);
            onClick.handle(event);
        });

        return button;
    }

    public static Pane buttonWithIcon(Icon icon, String text, EventHandler<? super MouseEvent> onClick) {
        AnchorPane button = new AnchorPane();

        Label label = new Label(text);
        label.setPadding(new Insets(0, 16, 0, 16));
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

        button.setOnMousePressed(event -> AudioManager.playEffect("assets/effects/click.wav"));
        button.setOnMouseClicked(event -> {
            Animations.scale(button, 1.1, 1.0, 0.1);
            onClick.handle(event);
        });

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

        iconButton.setOnMousePressed(event -> AudioManager.playEffect("assets/effects/click.wav"));

        iconButton.setOnMouseClicked(event -> {
            Animations.scale(iconButton, 1.1, 1.0, 0.1);
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

    public static TextField usernameField() {
        TextField field = new TextField();

        field.setAlignment(Pos.CENTER);
        field.getStyleClass().add("FIR_textfield");
        return field;
    }

    public static TextField passwordField() {
        PasswordField field = new PasswordField();

        field.setAlignment(Pos.CENTER);
        field.getStyleClass().add("FIR_textfield");
        return field;
    }

    public static Label text(String text, TextStyle style, Color color) {
        Label label = new Label(text);

        if (style == TextStyle.TITLE) {
            StackPane.setAlignment(label, Pos.TOP_CENTER);
        }

        label.getStyleClass().add(style.styleClass);

        label.setTextOverrun(OverrunStyle.CLIP);
        label.setTextFill(color);

        return label;
    }

    public static Label text(String text, TextStyle style) {
        return text(text, style, Color.WHITE);
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
                AudioManager.playEffect("assets/effects/bruh.wav");
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
                AudioManager.playEffect("assets/effects/ouai.wav");
                StageManager.background().julien();
            } else if (click_count.get() > 1) {
                AudioManager.playEffect("assets/effects/bruh.wav");
            }
        });

        return buzzer;
    }

    public static Node slider(Getter<Double> getValue, Consumer<Double> onValueChange) {
        Slider slider = new Slider();

        slider.setMin(0);
        slider.setMax(1);
        slider.setValue(getValue.call());

        final int[] lastTick = { (int) (getValue.call() * 10) };
        final long[] lastTickTime = { System.currentTimeMillis() };

        slider.valueProperty().addListener(event -> {
            int tick = (int) (slider.getValue() * 15);
            int tickDown = (int) (Math.ceil(slider.getValue() * 15));
            int tickUp = (int) (Math.floor(slider.getValue() * 15));

            if (tickDown == 0) {
                AudioManager.playEffect("assets/effects/tick.wav", 0.7);

            } else if (tickUp == 15) {
                AudioManager.playEffect("assets/effects/tick.wav", 1);

            } else if (Math.abs(lastTick[0] - tick) >= 1 && lastTickTime[0] + 50 <= System.currentTimeMillis()) {
                lastTick[0] = tick;
                lastTickTime[0] = System.currentTimeMillis();

                AudioManager.playEffect("assets/effects/tick.wav", slider.getValue() * 0.1 + 0.8);
            }

            onValueChange.accept(slider.getValue());
        });

        return slider;
    }

    public static Region panel(Node content) {
        StackPane pane = new StackPane(content);

        pane.getStyleClass().add("panel");

        return pane;
    }

    public static Region backButton(EventHandler<? super MouseEvent> onClick)
    {
        Pane button = new Pane();
        //button.setStyle("-fx-border-color: green");

        button.setMaxWidth(96);
        button.setMaxHeight(48);

        StackPane icon = new StackPane();
        icon.setPrefWidth(48);
        icon.setPrefHeight(48);
        icon.setTranslateX(-8);

        ImageView image = new ImageView(Icon.ARROW_BACK.path);
        icon.getChildren().add(image);
        StackPane.setAlignment(image, Pos.CENTER);

        StackPane text = new StackPane();
        text.setPrefWidth(64);
        text.setPrefHeight(48);
        text.setTranslateX(-96);
        text.setOpacity(0);
        text.getChildren().add(text("Go Back", TextStyle.LABEL));

        button.getChildren().addAll(text, icon);

        button.setOnMouseEntered(event -> {
            Animations.translateX(icon, -8, 0 + 8, 0.1, 0, () -> {
                Animations.translateX(icon, 8, 0, 0.1);
            });

            Animations.translateX(text, -96, 48 + 8, 0.1, 0, () -> {
                Animations.translateX(text, 48 + 8, 48, 0.1);
            });

            Animations.fade(text, 0, 1, 0.05, 0);
        });
        
        button.setOnMouseExited(event -> {
            Animations.translateX(icon, 0, -8, 0.1, 0);
            Animations.translateX(text, 48, -96, 0.1, 0);
            Animations.fade(text, 1, 0, 0.05, 0);
        });

        button.setOnMousePressed(event -> {
            Animations.translateX(button, 0, -256, 0.1, 0, () -> onClick.handle(event));
            Animations.fade(button, 1, 0, 0.1, 0);
        });


        StackPane.setAlignment(button, Pos.CENTER_LEFT);

        return button;
    }
}
