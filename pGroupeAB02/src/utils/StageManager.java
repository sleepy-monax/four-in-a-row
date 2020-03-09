package utils;

import dialogs.Dialog;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.Animation;
import views.View;

public final class StageManager {
    private StageManager() {
    }

    private static boolean spinnerVisible;

    private static Stage stage;
    private static StackPane viewContainer;
    private static View currentView;
    private static Pane backgroud;
    private static Pane glitter;
    private static Pane spinner;

    public static final int DEFAULT_SCREEN_WIDTH = 960;
    public static final int DEFAULT_SCREEN_HEIGHT = 720;
    public static final String DEFAULT_STAGE_TITLE = "Four in a Row";
    public static final String DEFAULT_STAGE_ICON = "assets/big-buzzer.png";

    public static void initialize(Stage s) {
        stage = s;

        stage.setTitle(DEFAULT_STAGE_TITLE);

        stage.setWidth(DEFAULT_SCREEN_WIDTH);
        stage.setHeight(DEFAULT_SCREEN_HEIGHT);

        stage.setMinWidth(DEFAULT_SCREEN_WIDTH);
        stage.setMinHeight(DEFAULT_SCREEN_HEIGHT);

        stage.setFullScreenExitHint("");

        stage.getIcons().add(new Image(DEFAULT_STAGE_ICON));
        stage.setOnCloseRequest(windowEvent -> {
            Runtime.getRuntime().exit(0);
        });

        spinner = new Pane();
        spinner.setId("wheel");
        spinner.setMinHeight(350);
        spinner.setMinWidth(350);
        spinner.setMaxHeight(350);
        spinner.setMaxWidth(350);
        spinner.setScaleX(0);
        spinner.setScaleY(0);
        spinner.setOpacity(0.25);

        backgroud = new Pane();
        backgroud.setId("background");
        backgroud.setScaleX(1.25);
        backgroud.setScaleY(1.25);

        glitter = new Pane();
        glitter.setId("glitter");
        glitter.setScaleX(1.25);
        glitter.setScaleY(1.25);
        glitter.setOpacity(0);

        RotateTransition animation = new RotateTransition(Duration.seconds(16), spinner);
        animation.setByAngle(360);
        animation.setCycleCount(Transition.INDEFINITE);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.play();

        View dummy = new View() {
        };

        viewContainer = new StackPane();
        viewContainer.getChildren().add(backgroud);
        viewContainer.getChildren().add(spinner);
        viewContainer.getChildren().add(glitter);
        viewContainer.getChildren().add(dummy);
        currentView = dummy;

        Scene scene = new Scene(viewContainer);
        scene.setOnMouseMoved(event -> {
            double x = (event.getSceneX() - scene.getWidth() / 2) / scene.getWidth();
            double y = (event.getSceneY() - scene.getHeight() / 2) / scene.getHeight();

            double offsetx = scene.getWidth() * 0.25 * x;
            double offsety = scene.getHeight() * 0.25 * y;

            backgroud.setTranslateX(-offsetx);
            backgroud.setTranslateY(-offsety);

            glitter.setTranslateX(-offsetx * 0.75);
            glitter.setTranslateY(-offsety * 0.75);

            spinner.setTranslateX(-offsetx * 0.5);
            spinner.setTranslateY(-offsety * 0.5);
        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && event.isAltDown()) {
                stage.setFullScreen(!stage.isFullScreen());
                event.consume();
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void showSpinner() {
        if (!spinnerVisible && spinner != null) {
            spinnerVisible = true;

            Animation.scale(spinner, 0, 10, 0.5);
            Animation.fade(glitter, 0, 0.25, 0.5);
        }
    }

    public static void hideSpinner() {
        if (spinnerVisible && spinner != null) {
            spinnerVisible = false;

            Animation.scale(spinner, 10, 0, 0.5);
            Animation.fade(glitter, 0.25, 0, 0.5);
        }
    }

    public static void switchView(View nextView) {
        currentView.onSwitchOut();

        viewContainer.getChildren().add(nextView);

        Animation.fade(nextView, 0, 1, 0.25, () -> {
            viewContainer.getChildren().remove(currentView);
            currentView = nextView;
            nextView.onSwitchIn();
        });

        Animation.scale(nextView, 4, 1, 0.25);
        Animation.fade(currentView, 1, 0, 0.25);
    }

    public static void showDialog(Dialog dialog) {
        com.sun.javafx.tk.Toolkit.getToolkit().checkFxUserThread();

        StackPane dialogContainer = new StackPane();
        dialogContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.25)");
        dialogContainer.setPadding(new Insets(32));
        dialogContainer.getChildren().add(dialog);

        StackPane.setAlignment(dialog, Pos.CENTER);

        viewContainer.getChildren().add(dialogContainer);

        Animation.offsetY(dialog, 128, 0, 0.1);
        Animation.fade(dialogContainer, 0, 1, 0.1);

        com.sun.javafx.tk.Toolkit.getToolkit().enterNestedEventLoop(dialog);

        Animation.offsetY(dialog, 0, 128, 0.1);
        Animation.fade(dialogContainer, 1, 0, 0.1, () -> {
            viewContainer.getChildren().remove(dialogContainer);
        });

    }

    public static void setTitle(String title) {
        stage.setTitle(title);
    }

    public static void quit() {
        stage.close();
    }
}