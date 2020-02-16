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
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(new MainMenu());
        scene.getStylesheets().addAll("assets/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
