package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.StageManager;

public class Settings extends View {
    public Settings(){
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(32));

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> StageManager.goToMainMenu());
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(menuContainer, backButton);
    }
}
