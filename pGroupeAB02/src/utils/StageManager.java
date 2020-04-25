package utils;

import views.widgets.Background;
import views.dialogs.Dialog;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import views.View;

public final class StageManager {
    public static final int DEFAULT_SCREEN_WIDTH = 960;
    public static final int DEFAULT_SCREEN_HEIGHT = 768;
    public static final String DEFAULT_STAGE_TITLE = "Four in a Row";
    public static final String DEFAULT_STAGE_ICON = "assets/images/big-buzzer.png";
    private static Stage stage;
    private static StackPane viewContainer;
    private static StackPane dialogContainer;
    private static Background background;
    private static View currentView;
    private static Timer backgroundTimer;

    private StageManager() {
    }

    public static void initialize(Stage stage) {
        StageManager.stage = stage;

        StageManager.stage.setTitle(DEFAULT_STAGE_TITLE);

        StageManager.stage.setWidth(DEFAULT_SCREEN_WIDTH);
        StageManager.stage.setHeight(DEFAULT_SCREEN_HEIGHT);

        StageManager.stage.setMinWidth(DEFAULT_SCREEN_WIDTH);
        StageManager.stage.setMinHeight(DEFAULT_SCREEN_HEIGHT);

        StageManager.stage.setFullScreenExitHint("");

        StageManager.stage.getIcons().add(new Image(DEFAULT_STAGE_ICON));

        View dummy = new View(false) {
        };

        dummy.setStyle("-fx-background-color: black;");

        viewContainer = new StackPane();
        dialogContainer = new StackPane();
        dialogContainer.setDisable(true);
        dialogContainer.setOpacity(0);
        dialogContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
        dialogContainer.setPadding(new Insets(32));

        background = new Background();

        backgroundTimer = new Timer();
        TimerTask backgroundTickTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    background().tick();
                });
            }
        };

        backgroundTimer.schedule(backgroundTickTask, 0, 1000 / 32);

        StageManager.stage.setOnCloseRequest(windowEvent -> {
            Runtime.getRuntime().exit(0);
        });

        viewContainer.getChildren().add(dummy);

        currentView = dummy;

        Scene scene = new Scene(new StackPane(background, viewContainer, dialogContainer), Color.BLACK);

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && event.isAltDown()) {
                SettingsManager.get().toggleGraphicFullscreen();
                SettingsManager.save();

                event.consume();
            }
        });

        scene.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            background.animate(event.getX(), event.getY());
        });

        StageManager.stage.setScene(scene);
        StageManager.stage.show();
    }

    public static void switchView(View nextView) {
        currentView.onSwitchOut();
        currentView.setDisable(true);

        viewContainer.getChildren().add(nextView);
        nextView.setDisable(true);
        nextView.onAttach();

        Animations.fade(0, 1, 0.25, () -> {
            currentView.onDettach();
            viewContainer.getChildren().remove(currentView);
            currentView = nextView;
            nextView.onSwitchIn();
            nextView.setDisable(false);
        }, nextView);

        Animations.scale(nextView, 4, 1, 0.25);
        Animations.fade(1, 0, 0.25, currentView);
    }


    public static void goBackTo(View nextView) {
        currentView.onSwitchOut();
        currentView.setDisable(true);

        viewContainer.getChildren().add(nextView);
        nextView.setDisable(true);
        nextView.onAttach();

        Animations.fade(0, 1, 0.25, () -> {
            currentView.onDettach();
            viewContainer.getChildren().remove(currentView);
            currentView = nextView;
            nextView.onSwitchIn();
            nextView.setDisable(false);
        }, nextView);

        Animations.translateX(nextView, -DEFAULT_SCREEN_WIDTH, 0, 0.25);

        Animations.fade(1, 0, 0.25, currentView);
        Animations.translateX(currentView, 0, DEFAULT_SCREEN_WIDTH, 0.25);

        AudioManager.playEffect("assets/effects/woosh.wav");
    }

    public static void goTo(View nextView) {
        currentView.onSwitchOut();
        currentView.setDisable(true);

        viewContainer.getChildren().add(nextView);
        nextView.setDisable(true);
        nextView.onAttach();

        Animations.fade(0, 1, 0.25, () -> {
            currentView.onDettach();
            viewContainer.getChildren().remove(currentView);
            currentView = nextView;
            nextView.onSwitchIn();
            nextView.setDisable(false);
        }, nextView);

        Animations.translateX(nextView, DEFAULT_SCREEN_WIDTH, 0, 0.25);

        Animations.fade(1, 0, 0.25, currentView);
        Animations.translateX(currentView, 0, -DEFAULT_SCREEN_WIDTH, 0.25);

        AudioManager.playEffect("assets/effects/woosh.wav");
    }

    public static void showDialog(Dialog dialog) {
        com.sun.javafx.tk.Toolkit.getToolkit().checkFxUserThread();

        dialogContainer.setDisable(false);
        dialogContainer.getChildren().add(dialog);
        StackPane.setAlignment(dialog, Pos.CENTER);

        Animations.fade(0, 1, 0.1, dialogContainer);
        Animations.translateY(128, 0, 0.1, dialog);

        AudioManager.playEffect("assets/effects/woosh.wav");
        viewContainer.setEffect(new BoxBlur(8, 8, 2));
        com.sun.javafx.tk.Toolkit.getToolkit().enterNestedEventLoop(dialog);
        viewContainer.setEffect(null);
        AudioManager.playEffect("assets/effects/woosh.wav");

        Animations.translateY(0, 128, 0.1, dialog);
        Animations.fade(1, 0, 0.1, () -> {
            dialogContainer.getChildren().remove(dialog);
            dialogContainer.setDisable(true);
        }, dialogContainer);
    }

    public static Background background() {
        return background;
    }

    public static void setTitle(String title) {
        stage.setTitle(title);
    }

    public static void quit() {
        System.out.println("Quitting...");
        backgroundTimer.cancel();
        stage.close();
    }

    public static void setFullscreen(boolean graphicFullscreen) {
        stage.setFullScreen(graphicFullscreen);

    }
}