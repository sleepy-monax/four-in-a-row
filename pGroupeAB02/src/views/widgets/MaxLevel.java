package views.widgets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class MaxLevel extends Label {

    public MaxLevel(int value){
        this.getStyleClass().add("maxLevel");
        this.setAlignment(Pos.CENTER);
        setText("Your Max Level is: \n"+ value);
    }

    public void update(int value){
        setText("Your Max Level is: \n"+ value);
    }

}
