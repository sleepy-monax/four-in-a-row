package views.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
import views.dialogs.EditQuestion;
import views.dialogs.InfoDialog;
import views.dialogs.OkCancel;

import static views.Layout.*;
import static views.Widget.*;

public class Editor extends View {

    private static TableView createTable(ObservableList<Question> data)
    {
        TableView table = new TableView();
        table.setEditable(true);

        table.setMinWidth(512);
        table.setMaxHeight(480);

        TableColumn<Question, String> answerColumn = new TableColumn <Question, String>("Answer");
        TableColumn<Question, String> themeColumn  = new TableColumn <Question, String>("Theme");
        TableColumn<Question, String> authorColumn = new TableColumn <Question, String>("Author");

        //Edit authorColumn
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setCellFactory(TextFieldTableCell. forTableColumn());
        authorColumn.setOnEditCommit((CellEditEvent <Question, String> event) -> {
            TablePosition <Question, String> pos = event.getTablePosition();

            String newAuthor =  event.getNewValue();

            int row = pos.getRow();
            Question question =  event.getTableView().getItems().get(row);

            question.setAuthor(newAuthor);
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
        });

        table.getColumns().addAll(authorColumn, answerColumn, themeColumn);
        table.setItems(data);

        return table;
    }

    public Editor(Deck deck) {
        super(true);

        ObservableList<Question> data = FXCollections.observableArrayList(deck.getQuestions());
        TableView table = createTable(data);

        Node createQuestionButton = buttonWithIcon(
            Icon.ADD,
            "Create",
            event -> {
                Question newQuestion = new Question();

                if (new EditQuestion(newQuestion).show() == OkCancel.OK)
                {
                    deck.add(newQuestion);
                    data.clear();
                    data.addAll(deck.getQuestions());
                }
            }
        );

        Node editQuestionButton = buttonWithIcon(
            Icon.EDIT,
            "Edit",
            event -> {
                if(table.getSelectionModel().getSelectedIndex() < 0)
                {
                    new InfoDialog("Something went wrong...","No row where selected").show();
                    return;
                }

                Question originalQuestion =  deck.getQuestions().get(table.getSelectionModel().getSelectedIndex());
                Question editedQuestion = originalQuestion.clone();

                if (new EditQuestion(editedQuestion).show() == OkCancel.OK)
                {
                    deck.replace(originalQuestion, editedQuestion);

                    data.clear();
                    data.addAll(deck.getQuestions());
                }
            }
        );

        Node deleteQuestionButton = iconButton(
            Icon.DELETE_FOREVER,
            event -> {
                if(table.getSelectionModel().getSelectedIndex() < 0)
                {
                    new InfoDialog("Something went wrong...","No row where selected").show();
                    return;
                }

                deck.remove(table.getSelectionModel().getSelectedIndex());
                data.clear();
                data.addAll(deck.getQuestions());
            }
        );

        Region editorPane = panel(
            vertical(
                16,
                horizontallyCentered(text("Question editor", TextStyle.TITLE)),
                spacer(16),
                fillWith(table),
                spacer(8),
                horizontal(16, Pos.CENTER, fillWith(createQuestionButton), fillWith(editQuestionButton), fillWith(deleteQuestionButton))
            )
        );

        getChildren().addAll(
            verticallyCentered(width(512,editorPane)),
            backButton(actionEvent -> {
                StageManager.switchView(new Main());
                deck.save();
            }));
    }
}