package views.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import models.Question;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
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
        table.setEditable(true);

        table.setMaxWidth(512);
        table.setMaxHeight(480);

        TableColumn <Question, String> answerColumn = new TableColumn <Question, String>("Answer");
        TableColumn <Question, String> themeColumn = new TableColumn <Question, String>("Theme");
        TableColumn <Question, String> authorColumn = new TableColumn <Question, String>("Author");
        TableColumn cluesColumn = new TableColumn("Clues");

        //Edit authorColumn
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setCellFactory(TextFieldTableCell. forTableColumn());
        authorColumn.setOnEditCommit((CellEditEvent <Question, String> event) -> {
            TablePosition <Question, String> pos = event.getTablePosition();

            String newAuthor =  event.getNewValue();

            int row = pos.getRow();
            Question question =  event.getTableView().getItems().get(row);

            question.setAuthor(newAuthor);
            deck.save();
        });


        // Edit themeColumn
        themeColumn.setCellValueFactory(new PropertyValueFactory<>("theme"));
        themeColumn.setCellFactory(TextFieldTableCell.<Question> forTableColumn());
        themeColumn.setOnEditCommit((CellEditEvent <Question, String> event) -> {
            TablePosition <Question, String> pos = event.getTablePosition();

            String newTheme =  event.getNewValue();

            int row = pos.getRow();
            Question question =  event.getTableView().getItems().get(row);

            question.setTheme(newTheme);
            deck.save();
        });
        //Edit cluesColumn
        cluesColumn.setCellValueFactory(new PropertyValueFactory<>("clues"));
        // Edit answerColumn
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));
        answerColumn.setCellFactory(TextFieldTableCell. forTableColumn());
        answerColumn.setOnEditCommit((CellEditEvent<Question, String> event) ->{
            TablePosition <Question, String> pos= event.getTablePosition();

            String newAnswer= event.getNewValue();

            int row=pos.getRow();

            Question question= event.getTableView().getItems().get(row);
            question.setAnswer(newAnswer);
            deck.save();
        });

        ObservableList< Question> list = FXCollections.observableArrayList(deck.getQuestions());

        table.setItems(list);

        table.getColumns().addAll(answerColumn, themeColumn, authorColumn, cluesColumn);


        Button backButton = Widget.button("Go back", actionEvent -> StageManager.switchView(new Main()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        getChildren().addAll(Widget.text("Editor", TextStyle.TITLE), table, backButton);
    }


}