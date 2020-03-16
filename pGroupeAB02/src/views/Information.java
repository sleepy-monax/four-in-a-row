package views;

import controls.Title;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import utils.StageManager;

public class Information extends View {
    public Information(String title, String message) {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(32));

        Button backButton = Widgets.makeButton("Go back to menu");
        backButton.setOnAction(actionEvent -> StageManager.switchView(new MainMenu()));

        VBox menuContainer = new VBox(128, new Title(title), Widgets.makeLabel(message), backButton) {
            {
                setAlignment(Pos.CENTER);
                setMaxWidth(512);
            }
        };

        this.getChildren().add(menuContainer);
    }
}