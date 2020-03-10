package views;

import controls.Title;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import models.Deck;
import utils.StageManager;

public class Editor extends View {
    public Editor(Deck deck) {
        setPadding(new Insets(32));

        TableView table = new TableView();

        table.setMaxWidth(512);
        table.setMaxHeight(480);

        TableColumn answerColumn = new TableColumn("Answer");
        TableColumn themeColumn = new TableColumn("Theme");
        TableColumn authorColumn = new TableColumn("Author");
        TableColumn cluesColumn = new TableColumn("Clues");

        table.getColumns().addAll(answerColumn, themeColumn, authorColumn, cluesColumn);

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> StageManager.switchView(new MainMenu()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        getChildren().addAll(new Title("Editor"), table, backButton);
    }
}