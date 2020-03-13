package utils;

import controller.AudioController;
import controls.Background;
import dialogs.Dialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import views.Animation;
import views.View;

public final class StageManager {
    private StageManager() {
    }

    private static Stage stage;
    private static StackPane viewContainer;
    private static StackPane dialogContainer;

    private static Background background;
    private static View currentView;

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

        View dummy = new View() {
        };

        dummy.setStyle("-fx-background-color: black;");

        viewContainer = new StackPane();
        dialogContainer = new StackPane();
        dialogContainer.setDisable(true);

        background = new Background(true);

        viewContainer.getChildren().add(background);
        viewContainer.getChildren().add(dummy);

        currentView = dummy;

        Scene scene = new Scene(new StackPane(viewContainer, dialogContainer));

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && event.isAltDown()) {
                stage.setFullScreen(!stage.isFullScreen());
                event.consume();
            }
        });

        scene.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            background.animate(event.getX(), event.getY());
        });

        stage.setScene(scene);
        stage.show();
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

        dialogContainer.setDisable(false);
        dialogContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
        dialogContainer.setPadding(new Insets(32));
        dialogContainer.getChildren().add(dialog);

        viewContainer.setEffect(new BoxBlur(8, 8, 2));

        StackPane.setAlignment(dialog, Pos.CENTER);

        Animation.offsetY(dialog, 128, 0, 0.1);
        Animation.fade(dialogContainer, 0, 1, 0.1);

        AudioController.playEffect("assets/woosh.wav");
        com.sun.javafx.tk.Toolkit.getToolkit().enterNestedEventLoop(dialog);
        AudioController.playEffect("assets/woosh.wav");

        Animation.offsetY(dialog, 0, 128, 0.1);
        Animation.fade(dialogContainer, 1, 0, 0.1, () -> {
            dialogContainer.getChildren().remove(dialog);
            viewContainer.setEffect(null);
            dialogContainer.setDisable(true);
        });
    }

    public static Background background() {
        return background;
    }

    public static void setTitle(String title) {
        stage.setTitle(title);
    }

    public static void quit() {
        stage.close();
    }
}