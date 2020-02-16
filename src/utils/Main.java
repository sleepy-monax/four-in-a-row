package utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.MainMenu;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new MainMenu());
        scene.getStylesheets().addAll("assets/style.css");

        primaryStage.setTitle("Four in a Row");

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
