package views.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Question;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import models.Deck;
import utils.StageManager;
import views.TextStyle;
import views.View;
import views.Widget;

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

        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        themeColumn.setCellValueFactory(new PropertyValueFactory<>("theme"));
        cluesColumn.setCellValueFactory(new PropertyValueFactory<>("clues"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));
        ObservableList< Question> list = FXCollections.observableArrayList(deck.getQuestions());

       table.setItems(list);

        table.getColumns().addAll(answerColumn, themeColumn, authorColumn, cluesColumn);


        Button backButton = Widget.button("Go back", actionEvent -> StageManager.switchView(new Main()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        getChildren().addAll(Widget.text("Editor", TextStyle.TITLE), table, backButton);
    }


}