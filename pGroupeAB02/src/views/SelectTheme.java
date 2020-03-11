package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Game;

import java.util.List;

import controls.Title;

public class SelectTheme extends View {

    public SelectTheme(Game game, List<String> themes) {
        this.setPadding(new Insets(32));

        VBox themesList = new VBox(16);

        themesList.setAlignment(Pos.CENTER);
        themesList.setMaxWidth(512);

        for (String theme : themes) {
            Pane themeButton = Widgets.makeBigButton(Icon.STAR, theme);
            themeButton.setPadding(new Insets(0, 72, 0, 72));

            themeButton.setOnMouseClicked(event -> {
                game.selectTheme(theme);
            });

            themesList.getChildren().add(themeButton);
        }

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> game.finish());
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(new Title("Select a theme"), themesList, backButton);
    }
}
