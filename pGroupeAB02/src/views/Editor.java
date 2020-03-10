package views;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Deck;

public class Editor extends View {
    public Editor(Deck deck) {
        TableView table = new TableView();

        TableColumn answerColumn = new TableColumn("Answer");
        TableColumn themeColumn = new TableColumn("Theme");
        TableColumn authorColumn = new TableColumn("Author");
        TableColumn cluesColumn = new TableColumn("Clues");

        table.getColumns().addAll(answerColumn, themeColumn, authorColumn, cluesColumn);

        getChildren().add(table);
    }
}