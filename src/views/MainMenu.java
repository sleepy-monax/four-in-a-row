package views;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends HBox {
    public MainMenu()
    {
        this.setAlignment(Pos.CENTER);
        this.setId("background");

        VBox root = new VBox();
        root.setPrefWidth(840);

        root.getChildren().add(new ImageView(new Image("assets/logo.png")));

        setFillHeight(true);
        getChildren().add(root);
    }
}
