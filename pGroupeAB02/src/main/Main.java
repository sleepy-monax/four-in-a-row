package main;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.StageManager;
import utils.ThreadManager;
import views.SplashScreen;
import controller.AudioController;

public class Main extends Application {

    public static Main app;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet("assets/style.css");

        ThreadManager.initialize();
        AudioController.initialize();
        StageManager.initialize(stage);

        StageManager.switchView(new SplashScreen());
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Shutting down...");
        ThreadManager.shutdown();
        AudioController.shutdown();

        // make that there is no object left preventing the process to exit
        Runtime.getRuntime().runFinalization();
    }
}
