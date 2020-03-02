package utils;

import javafx.application.Application;
import javafx.stage.Stage;
import views.SplashScreen;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet("assets/style.css");

        ThreadManager.Initialize();
        StageManager.initialize(stage);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Shutting down...");
                ThreadManager.shutdown();
            }
        });

        StageManager.switchScene(new SplashScreen());
    }
}
