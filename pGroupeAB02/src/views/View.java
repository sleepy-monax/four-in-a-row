package views;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import utils.StageManager;

public abstract class View extends StackPane {
    public View(boolean withPadding) {
        if (withPadding)
        {
            this.setPadding(new Insets(32));
            this.setMaxWidth(StageManager.DEFAULT_SCREEN_WIDTH);
        }
    }

    public void onAttach() {
    }

    public void onDettach() {
    }

    public void onSwitchIn() {
    }

    public void onSwitchOut() {
    }
}