package views;

import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import utils.StageManager;

public class Information extends StackPane {
    public Information(String title, String message) {
        this.setAlignment(Pos.CENTER);
        this.setId("background");
        this.setPadding(new Insets(32));

        Button backButton = Widgets.makeButton("Go back to menu");
        backButton.setOnAction(actionEvent -> StageManager.switchScene(new MainMenu()));

        VBox menuContainer = new VBox(16, Widgets.makeTitle(title), Widgets.makeLabel(message), backButton) {
            {
                setAlignment(Pos.CENTER);
                setMaxWidth(512);
            }
        };

        this.getChildren().add(menuContainer);
    }
}