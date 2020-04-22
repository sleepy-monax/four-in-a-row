package views.widgets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class ActualScore extends Label {

    public ActualScore(int value){
        this.getStyleClass().add("actualScore");
        this.setAlignment(Pos.CENTER);
        setText("Your score is: \n"+ value);
    }

    public void update(int value) {
        setText("Your score is: \n"+value);
    }
}
