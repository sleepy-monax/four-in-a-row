package utils;

import java.io.File;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import sun.audio.AudioPlayer;
import views.SplashScreen;
import controller.AudioController;

public class Main extends Application {
	
    public static void main(String[] args) {
        launch(args);
        
    }

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet("assets/style.css");

        ThreadManager.Initialize();
        StageManager.initialize(stage);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            ThreadManager.shutdown();
        }));

        StageManager.switchScene(new SplashScreen());

        AudioController.AudioPlay();
    }
}


