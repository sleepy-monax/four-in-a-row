package views;

import javafx.scene.control.TableView;

public class Editor extends View {
    public Editor() {
        getChildren().add(new TableView<>());
    }
}