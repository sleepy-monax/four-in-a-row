package utils;

import controller.AudioController;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.MainMenu;
import views.View;

public final class StageManager {
    private StageManager() {
    }

    private static Stage stage;
    private static StackPane viewContainer;
    private static View currentView;
    private static Pane spinner;
    private static Pane backgroud;
    private static Pane glitter;

    public static final int DEFAULT_SCREEN_WIDTH = 960;
    public static final int DEFAULT_SCREEN_HEIGHT = 720;
    public static final String DEFAULT_STAGE_TITLE = "Four in a Row";
    public static final String DEFAULT_STAGE_ICON = "assets/big-buzzer.png";

    public static void showSpinner() {
        ScaleTransition scale = new ScaleTransition();
        scale.setFromX(0);
        scale.setToX(10);
        scale.setFromY(0);
        scale.setToY(10);
        scale.setDuration(Duration.seconds(2.5));
        scale.setNode(spinner);
        scale.setOnFinished((event) -> {
            spinner.setScaleX(10);
            spinner.setScaleY(10);
        });
        scale.play();

        FadeTransition fade = new FadeTransition(Duration.seconds(1));
        fade.setFromValue(0);
        fade.setToValue(0.25);
        fade.setNode(glitter);
        fade.setOnFinished(e -> {
            glitter.setOpacity(0.25);
        });
        fade.play();
    }

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

        RotateTransition animation = new RotateTransition(Duration.millis(16000), spinner);
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

            double offsetx = scene.getWidth() * 0.25 * x * 0.5;
            double offsety = scene.getHeight() * 0.25 * y * 0.5;

            backgroud.setTranslateX(-offsetx);
            backgroud.setTranslateY(-offsety);

            spinner.setTranslateX(-offsetx * 0.25);
            spinner.setTranslateY(-offsety * 0.25);

            glitter.setTranslateX(-offsetx * 0.5);
            glitter.setTranslateY(-offsety * 0.5);
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

    public static void switchView(View nextView) {
        currentView.onSwitchOut();

        viewContainer.getChildren().add(nextView);

        FadeTransition fadingNext = new FadeTransition();

        fadingNext.setFromValue(0);
        fadingNext.setToValue(1);
        fadingNext.setNode(nextView);
        fadingNext.setDuration(Duration.seconds(0.25));

        fadingNext.setOnFinished(actionEvent -> {
            viewContainer.getChildren().remove(currentView);
            currentView = nextView;
            nextView.onSwitchIn();
        });

        ScaleTransition scaleNext = new ScaleTransition();

        scaleNext.setFromX(4);
        scaleNext.setToX(1);
        scaleNext.setFromY(4);
        scaleNext.setToY(1);
        scaleNext.setInterpolator(Interpolator.EASE_IN);

        scaleNext.setNode(nextView);
        scaleNext.setDuration(Duration.seconds(0.25));

        FadeTransition fadingCurrent = new FadeTransition();

        fadingCurrent.setFromValue(1);
        fadingCurrent.setToValue(0);
        fadingCurrent.setNode(currentView);
        fadingCurrent.setDuration(Duration.seconds(0.25));

        fadingCurrent.play();
        fadingNext.play();
        scaleNext.play();
    }

    public static void setTitle(String title) {
        stage.setTitle(title);
    }

    public static void goToMainMenu() {
        StageManager.switchView(new MainMenu());
        AudioController.playNow("assets/transition.wav", () -> {
            AudioController.playLoopNow("assets/loop2.wav");
        });
    }

    public static void quit() {
        stage.close();
    }

}