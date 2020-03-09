package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.StageManager;

import java.util.List;

import controls.Title;

public class SelectTheme extends View {

    public SelectTheme(List<String> themes, int nbTheme) {
        this.setPadding(new Insets(32));

        VBox themesList = new VBox(16);

        themesList.setAlignment(Pos.CENTER);
        themesList.setMaxWidth(512);

        for (int i = 0; i < nbTheme; i++) {
            Pane themeButton = Widgets.makeBigButton(Icon.STAR, "Thème " + (i + 1) /* lTheme.get(i).toString() */);
            themeButton.setPadding(new Insets(0, 72, 0, 72));

            themeButton.setOnMouseClicked(event -> {
                StageManager.switchView(new MainGame());
            });

            themesList.getChildren().add(themeButton);
        }

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> StageManager.switchView(new MainMenu()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(new Title("Select a theme"), themesList, backButton);
    }
}
