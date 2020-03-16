package views.widgets;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import utils.Getter;
import utils.Icon;

public class IconButton extends StackPane {
    public IconButton( Getter<Icon> getIcon, EventHandler<? super MouseEvent> onClick)
    {
        getStyleClass().addAll("button", "FIR_orb-button");

        ImageView image = new ImageView(getIcon.call().path);
        getChildren().add(image);
        StackPane.setAlignment(image, Pos.CENTER);

        this.setOnMouseClicked(event -> {
            onClick.handle(event);
            image.setImage(new Image(getIcon.call().path));
        });
    }
}
