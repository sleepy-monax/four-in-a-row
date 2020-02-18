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

        fading.setOnFinished(actionEvent ->
                stage.getScene().setRoot(new StackPane(root))
        );

        fading.play();
    }

    public static void quit() {
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        primaryStage.setTitle("Four in a Row");

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setFullScreen(true);

        primaryStage.getIcons().add(new Image("assets/buzzer.png"));

        Scene scene = new Scene(new Pane());
        scene.getStylesheets().addAll("assets/style.css");
        primaryStage.setScene(scene);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && event.isAltDown()) {
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
            }
        });

        switchScene(new SplashScreen());
        primaryStage.show();
    }
}
