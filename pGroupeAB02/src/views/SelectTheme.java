package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.StageManager;

import java.util.List;

public class SelectTheme extends View {

    public SelectTheme(List<String> lTheme, int nbTheme) {
        this.setPadding(new Insets(32));

        Pane theme1Button = Widgets.makeBigButton("assets/buzzer.png", "Thème 1" /* lTheme.get(0).toString() */);
        theme1Button.setPadding(new Insets(0, 72, 0, 72));

        Pane theme2Button = Widgets.makeBigButton("assets/buzzer.png", "Thème 2" /* lTheme.get(1).toString() */);
        theme2Button.setPadding(new Insets(0, 72, 0, 72));
        if (nbTheme < 2) {
            theme2Button.setDisable(true);
            theme2Button.setOpacity(0);
        }

        Pane theme3Button = Widgets.makeBigButton("assets/buzzer.png", "Thème 3" /* lTheme.get(2).toString() */);
        theme3Button.setPadding(new Insets(0, 72, 0, 72));
        if (nbTheme < 3) {
            theme3Button.setDisable(true);
            theme3Button.setOpacity(0);
        }

        VBox vbTheme = new VBox(16, theme1Button, theme2Button, theme3Button) {
            {
                setAlignment(Pos.CENTER);
                setMaxWidth(512);
            }
        };

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> StageManager.switchView(new MainMenu()));

        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(vbTheme, backButton);

        theme1Button.setOnMouseClicked(event -> {
            StageManager.switchView(new MainGame());
        });

        theme2Button.setOnMouseClicked(event -> {

        });

        theme3Button.setOnMouseClicked(event -> {

        });
    }
}
