package views.menu;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import models.Question;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import models.Deck;
import utils.Icon;
import utils.StageManager;
import views.TextStyle;
import views.View;
import views.dialogs.InfoDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static views.Layout.*;
import static views.Widget.*;

public class Editor extends View {

    private static TableView createTable(Deck deck)
    {
        ObservableList<Question> list = FXCollections.observableArrayList(deck.getQuestions());

        TableView table = new TableView();
        table.setEditable(true);

        table.setMinWidth(512);
        table.setMaxHeight(480);

        TableColumn<Question, String> answerColumn = new TableColumn <Question, String>("Answer");
        TableColumn<Question, String> themeColumn  = new TableColumn <Question, String>("Theme");
        TableColumn<Question, String> authorColumn = new TableColumn <Question, String>("Author");
        TableColumn<Question, String> cluesColumn1 = new TableColumn("Clues 1");
        TableColumn<Question, String> cluesColumn2 = new TableColumn("Clues 2");
        TableColumn<Question, String> cluesColumn3 = new TableColumn("Clues 3");

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

        table.getColumns().addAll(authorColumn, answerColumn, themeColumn);
        table.setItems(list);

        return table;
    }

    public Editor(Deck deck) {
        setPadding(new Insets(32));

        TableView table = createTable(deck);

        Node createQuestionButton = buttonWithIcon(
            Icon.ADD,
            "Create",
            event -> {

            }
        );

        Node deleteQuestionButton = buttonWithIcon(
            Icon.DELETE_FOREVER,
            "Delete",
            event -> {
                if(table.getSelectionModel().getSelectedIndex() < 0) {
                    new InfoDialog("Something went wrong...","No row where selected").show();
                }
                else{
                    deck.remove(table.getSelectionModel().getSelectedIndex());
                    table.getItems().remove(table.getSelectionModel().getSelectedIndex());
                    deck.save();
                    table.refresh();
                }
            }
        );

        Region editorPane = panel(
            vertical(
                16,
                horizontallyCentered(text("Question editor", TextStyle.TITLE)),
                spacer(16),
                fill(table),
                spacer(8),
                horizontal(16, Pos.CENTER, fill(createQuestionButton), fill(deleteQuestionButton))
            )
        );

        Button backButton = button("Go back", actionEvent -> StageManager.switchView(new Main()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        getChildren().addAll(verticallyCentered(width(512,editorPane)), backButton);
    }


}