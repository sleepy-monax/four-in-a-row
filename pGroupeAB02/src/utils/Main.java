package utils;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.SplashScreen;

public class Main extends Application {
    public static final int DEFAULT_SCREEN_WIDTH = 960;
    public static final int DEFAULT_SCREEN_HEIGHT = 720;

    private static Stage stage = null;

    public static void main(String[] args) {
        launch(args);
    }

    public static void switchScene(Parent root) {
        Parent oldroot = stage.getScene().getRoot();

        stage.getScene().setRoot(new StackPane(oldroot, root));

        FadeTransition fading = new FadeTransition();

        fading.setFromValue(0);
        fading.setToValue(1);
        fading.setNode(root);
        fading.setDuration(Duration.seconds(0.25));

        fading.setOnFinished(actionEvent -> stage.getScene().setRoot(new StackPane(root)));

        fading.play();
    }

    public static void setTitle(String title) {
        stage.setTitle(title);
    }

    public static void quit() {
        stage.close();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        stage.setTitle("Four in a Row");

        stage.setWidth(DEFAULT_SCREEN_WIDTH);
        stage.setHeight(DEFAULT_SCREEN_HEIGHT);
        stage.setMinWidth(DEFAULT_SCREEN_WIDTH);
        stage.setMinHeight(DEFAULT_SCREEN_HEIGHT);

        stage.setFullScreenExitHint(null);

        stage.getIcons().add(new Image("assets/big-buzzer.png"));
        stage.setOnCloseRequest(windowEvent -> {
            Runtime.getRuntime().exit(0);
        });

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutting down...");
                ThreadManager.shutdown();
            }
        });

        Scene scene = new Scene(new Pane());
        scene.getStylesheets().addAll("assets/style.css");
        stage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && event.isAltDown()) {
                stage.setFullScreen(!stage.isFullScreen());
                event.consume();
            }
        });

        switchScene(new SplashScreen());
        stage.show();
    }
}
