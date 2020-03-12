package controls;

import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

public class ClueCard extends StackPane {
    public ClueCard(String body) {
        Random rnd = new Random();

        Label bodyLabel = new Label(body);
        bodyLabel.getStyleClass().add("card-body");
        bodyLabel.setWrapText(true);
        bodyLabel.setAlignment(Pos.CENTER);
        bodyLabel.setTextAlignment(TextAlignment.CENTER);

        setRotate((rnd.nextDouble() - 0.5) * 4);
        getStyleClass().add("card");
        getChildren().add(bodyLabel);
    }
}