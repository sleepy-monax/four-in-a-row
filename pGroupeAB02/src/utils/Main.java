package utils;

import javafx.application.Application;
import javafx.stage.Stage;
import views.SplashScreen;
import controller.AudioController;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet("assets/style.css");

        ThreadManager.initialize();
        AudioController.initialize();
        StageManager.initialize(stage);

        StageManager.switchScene(new SplashScreen());
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Shutting down...");
        ThreadManager.shutdown();
        AudioController.shutdown();
    }
}
