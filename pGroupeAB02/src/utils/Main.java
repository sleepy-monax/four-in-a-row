package utils;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.SplashScreen;

public class Main extends Application {
    private static Stage stage = null;

    public static void main(String[] args) {
        launch(args);
    }

    public static void switchScene(Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll("assets/style.css");
        stage.setScene(scene);
    }

    public static void quit()
    {
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        primaryStage.setTitle("Four in a Row");

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        switchScene(new SplashScreen());
        primaryStage.show();
    }
}
