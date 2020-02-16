package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainMenu extends HBox {
    public MainMenu()
    {
        this.setAlignment(Pos.CENTER);
        this.setId("background");

        VBox root = new VBox(16);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets( 64));

        Pane logo = new Pane();
        logo.setId("logo");


        root.getChildren().add(logo);
        root.getChildren().add(Widgets.makeButton("Singleplayer"));
        root.getChildren().add(Widgets.makeButton("Multiplayer"));

        setFillHeight(true);
        getChildren().add(root);
    }
}
