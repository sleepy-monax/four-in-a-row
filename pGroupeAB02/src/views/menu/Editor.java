package views.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
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
import views.Widget;
import views.dialogs.InfoDialog;

import java.util.concurrent.atomic.AtomicInteger;

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
        TableColumn cluesColumn1 = new TableColumn("Clues 1");
        TableColumn cluesColumn2 = new TableColumn("Clues 2");
        TableColumn cluesColumn3 = new TableColumn("Clues 3");

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

        //Edit cluesColumn 1
        cluesColumn1.setCellValueFactory(new PropertyValueFactory<>("clues"));
        //Edit cluesColumn 2
        //Edit cluesColumn 3
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

        table.getColumns().addAll(answerColumn, themeColumn, authorColumn, cluesColumn1, cluesColumn2, cluesColumn3);


        Button backButton = Widget.button("Go back", actionEvent -> StageManager.switchView(new Main()));

        Node orbAdd = Widget.iconButton(Icon.ADD,  actionEvent -> {

        });

        Node orbDelete = Widget.iconButton(Icon.DELETE_FOREVER,  actionEvent -> {
            if(table.getSelectionModel().getSelectedIndex()<0) {
                new InfoDialog("Error","Please, select a row").show();
            }
            else{
                deck.remove(table.getSelectionModel().getSelectedIndex());
                deck.save();
                StageManager.switchView(new Editor(Deck.load()));

            }

        });

        HBox orbContainer = new HBox(16, orbAdd, orbDelete);
        orbContainer.setAlignment(Pos.CENTER);
        orbContainer.setPrefHeight(48);
        orbContainer.setMaxHeight(48 + 24);
        orbContainer.setPadding(new Insets(24, 0, 0, 0));
        StackPane.setAlignment(orbContainer, Pos.BOTTOM_CENTER);

        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);


        getChildren().addAll(Widget.text("Editor", TextStyle.TITLE), table, backButton, orbContainer);
    }


}