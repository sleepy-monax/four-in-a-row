package main;

import utils.AudioManager;
import utils.SettingsManager;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.StageManager;
import utils.ThreadManager;
import views.screen.Splash;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet("assets/style.css");

        ThreadManager.initialize();
        AudioManager.initialize();
        StageManager.initialize(stage);
        SettingsManager.apply();

        StageManager.switchView(new Splash());
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Shutting down...");
        ThreadManager.shutdown();
        AudioManager.shutdown();

        // make that there is no object left preventing the process to exit
        Runtime.getRuntime().runFinalization();
    }
}
