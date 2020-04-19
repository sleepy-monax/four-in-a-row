package views.menu;

import javafx.beans.property.SimpleStringProperty;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Editor extends View {
    public Editor(Deck deck) {
        setPadding(new Insets(32));

        TableView table = new TableView();
        table.setEditable(true);

        table.setMinWidth(512);
        table.setMaxHeight(480);

        TableColumn <Question, String> answerColumn = new TableColumn <Question, String>("Answer");
        TableColumn <Question, String> themeColumn = new TableColumn <Question, String>("Theme");
        TableColumn <Question, String> authorColumn = new TableColumn <Question, String>("Author");
        TableColumn <Question, String> cluesColumn1 = new TableColumn("Clues 1");
        TableColumn <Question, String> cluesColumn2 = new TableColumn("Clues 2");
        TableColumn <Question, String> cluesColumn3 = new TableColumn("Clues 3");

        ObservableList< Question> list = FXCollections.observableArrayList(deck.getQuestions());

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
        cluesColumn1.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClues().get(0)));
        cluesColumn1.setCellFactory(TextFieldTableCell.<Question> forTableColumn());
        cluesColumn1.setOnEditCommit((CellEditEvent <Question, String> event) -> {
            TablePosition <Question, String> pos = event.getTablePosition();

            String newClues1 =  event.getNewValue();
            List newList= new ArrayList<>();
            int row = pos.getRow();
            newList.add(newClues1);
            newList.add(list.get(row).getClues().get(1));
            newList.add(list.get(row).getClues().get(2));
            Question question =  event.getTableView().getItems().get(row);

            question.setClues(newList);
            deck.save();
        });
        //Edit cluesColumn 2
        cluesColumn2.setCellValueFactory(param -> {
            if(param.getValue().getClues().size() > 1)
                return new SimpleStringProperty(param.getValue().getClues().get(1));
            else{
                return new SimpleStringProperty("");
            }
        });
        cluesColumn2.setCellFactory(TextFieldTableCell.<Question> forTableColumn());

        cluesColumn2.setOnEditCommit((CellEditEvent <Question, String> event) -> {
            TablePosition <Question, String> pos = event.getTablePosition();

            String newClues2 =  event.getNewValue();
            List newList= new ArrayList<>();
            int row = pos.getRow();

            newList.add(list.get(row).getClues().get(0));
            newList.add(newClues2);
            newList.add(list.get(row).getClues().get(2));
            Question question =  event.getTableView().getItems().get(row);

            question.setClues(newList);
            deck.save();
        });
        //Edit cluesColumn 3
        cluesColumn3.setCellValueFactory(param -> {
            if(param.getValue().getClues().size() > 2)
                return new SimpleStringProperty(param.getValue().getClues().get(2));
            else{
                return new SimpleStringProperty("");
            }
        });
        cluesColumn3.setCellFactory(TextFieldTableCell.<Question> forTableColumn());

        cluesColumn3.setOnEditCommit((CellEditEvent <Question, String> event) -> {
            TablePosition <Question, String> pos = event.getTablePosition();

            String newClues3 =  event.getNewValue();
            List newList= new ArrayList<>();
            int row = pos.getRow();

            newList.add(list.get(row).getClues().get(0));
            newList.add(list.get(row).getClues().get(1));
            newList.add(newClues3);
            Question question =  event.getTableView().getItems().get(row);

            question.setClues(newList);
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
        orbContainer.setMaxWidth(48);
        orbContainer.setMaxHeight(48 + 24);
        orbContainer.setPadding(new Insets(24, 0, 0, 0));
        StackPane.setAlignment(orbContainer, Pos.BOTTOM_CENTER);

        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);


        getChildren().addAll(Widget.text("Editor", TextStyle.TITLE), table, backButton, orbContainer);
    }


}