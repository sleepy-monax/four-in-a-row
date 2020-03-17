package utils;

import views.widgets.Background;
import views.dialogs.Dialog;
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
    public static final int DEFAULT_SCREEN_HEIGHT = 720;
    public static final String DEFAULT_STAGE_TITLE = "Four in a Row";
    public static final String DEFAULT_STAGE_ICON = "assets/big-buzzer.png";
    private static Stage stage;
    private static StackPane viewContainer;
    private static StackPane dialogContainer;
    private static Background background;
    private static View currentView;
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
        StageManager.stage.setOnCloseRequest(windowEvent -> {
            Runtime.getRuntime().exit(0);
        });

        View dummy = new View() {
        };

        dummy.setStyle("-fx-background-color: black;");

        viewContainer = new StackPane();
        dialogContainer = new StackPane();
        dialogContainer.setDisable(true);
        dialogContainer.setOpacity(0);
        dialogContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
        dialogContainer.setPadding(new Insets(32));

        background = new Background(true);

        viewContainer.getChildren().add(background);
        viewContainer.getChildren().add(dummy);

        currentView = dummy;

        Scene scene = new Scene(new StackPane(viewContainer, dialogContainer), Color.BLACK);

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && event.isAltDown()) {
                StageManager.stage.setFullScreen(!StageManager.stage.isFullScreen());
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

        viewContainer.getChildren().add(nextView);

        Animations.fade(0, 1, 0.25, () -> {
            viewContainer.getChildren().remove(currentView);
            currentView = nextView;
            nextView.onSwitchIn();
        }, nextView);

        Animations.scale(4, 1, 0.25, nextView);
        Animations.fade(1, 0, 0.25, currentView);
    }

    public static void showDialog(Dialog dialog) {
        com.sun.javafx.tk.Toolkit.getToolkit().checkFxUserThread();

        dialogContainer.setDisable(false);
        dialogContainer.getChildren().add(dialog);
        StackPane.setAlignment(dialog, Pos.CENTER);

        Animations.fade(0, 1, 0.1, dialogContainer);
        Animations.translateY(128, 0, 0.1, dialog);

        AudioManager.playEffect("assets/woosh.wav");
        viewContainer.setEffect(new BoxBlur(8, 8, 2));
        com.sun.javafx.tk.Toolkit.getToolkit().enterNestedEventLoop(dialog);
        viewContainer.setEffect(null);
        AudioManager.playEffect("assets/woosh.wav");

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
        stage.close();
    }
}