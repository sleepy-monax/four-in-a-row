package views.dialogs;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import utils.StageManager;

public class Dialog<ResultType> extends StackPane {
    private ResultType result;

    public Dialog() {
        super();

        this.getStyleClass().add("dialog");
        this.setPadding(new Insets(16));
        this.setMaxWidth(512);
        this.setMaxHeight(256);
    }

    public ResultType show() {
        StageManager.showDialog(this);
        return result;
    }

    public void resume(ResultType result) {
        this.result = result;
        com.sun.javafx.tk.Toolkit.getToolkit().exitNestedEventLoop(this, null);
    }
}