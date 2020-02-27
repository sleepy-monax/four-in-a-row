package utils;

import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public final class StageManager {
    private StageManager() {
    }

    private static Stage stage;

    public static final int DEFAULT_SCREEN_WIDTH = 960;
    public static final int DEFAULT_SCREEN_HEIGHT = 720;
    public static final String DEFAULT_STAGE_TITLE = "Four in a Row";
    public static final String DEFAULT_STAGE_ICON = "assets/big-buzzer.png";

    public static void initialize(Stage s) {
        assert (s == null);
        stage = s;

        stage.setTitle(DEFAULT_STAGE_TITLE);

        stage.setWidth(DEFAULT_SCREEN_WIDTH);
        stage.setHeight(DEFAULT_SCREEN_HEIGHT);

        stage.setMinWidth(DEFAULT_SCREEN_WIDTH);

        ; // Le point virgule de Shasha

        stage.setMinHeight(DEFAULT_SCREEN_HEIGHT);

        stage.setFullScreenExitHint(null);

        stage.getIcons().add(new Image(DEFAULT_STAGE_ICON));
        stage.setOnCloseRequest(windowEvent -> {
            Runtime.getRuntime().exit(0);
        });

        Pane root = new Pane();
        root.setId("background");
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll("assets/style.css");
        stage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && event.isAltDown()) {
                stage.setFullScreen(!stage.isFullScreen());
                event.consume();
            }
        });

        stage.show();
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

}