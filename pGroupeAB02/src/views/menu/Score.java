package views.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import models.Player;
import utils.StageManager;
import views.TextStyle;
import views.View;

import static views.Widget.*;

public class Score extends View {
    TableView tableView = new TableView();

    public Score() {
        super(true);

        TableColumn<String, Player> column1 = new TableColumn<>("Position");
        column1.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<String, Player> column2 = new TableColumn<>("Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, Player> column3 = new TableColumn<>("Score");
        column3.setCellValueFactory(new PropertyValueFactory<>("score"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        this.getChildren().addAll(
            text("Score", TextStyle.TITLE),
            tableView,
            backButton(actionEvent -> StageManager.switchView(new Main()))
        );
    }
}
