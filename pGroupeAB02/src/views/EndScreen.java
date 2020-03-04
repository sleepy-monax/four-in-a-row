package views;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class EndScreen extends View {

    public EndScreen() {
        this.setStyle("-fx-background-color: black;");

        Pane spinner = new Pane();
        spinner.setId("wheel");
        spinner.setMinHeight(350);
        spinner.setMinWidth(350);
        spinner.setMaxHeight(350);
        spinner.setMaxWidth(350);
        spinner.setTranslateY(350);
        spinner.setScaleX(5);
        spinner.setScaleY(5);
        spinner.setScaleZ(5);
        spinner.setOpacity(0.1);

        StackPane.setAlignment(spinner, Pos.CENTER);

        RotateTransition animation = new RotateTransition(Duration.millis(16000), spinner);
        animation.setByAngle(360);
        animation.setCycleCount(Integer.MAX_VALUE);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.play();

        this.getChildren().addAll(spinner, Widgets.makeLogo());
    }

}