package views;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class EndScreen extends View {

    public EndScreen() {

        this.getChildren().addAll(new VBox(64, Widgets.makeLogo(), Widgets.makeLabel("See you soon")) {
            {
                setAlignment(Pos.CENTER);
                setMaxWidth(512);
            }
        });
    }

}