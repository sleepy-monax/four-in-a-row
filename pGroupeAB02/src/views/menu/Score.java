package views.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import models.Player;
import models.scores.ListScore;
import utils.StageManager;
import views.TextStyle;
import views.View;

import static views.Layout.*;
import static views.Widget.*;

public class Score extends View {
    public Score() {
        super(true);

        TableView table = new TableView();

        ObservableList<models.scores.Score> data = FXCollections.observableArrayList(ListScore.get().getScores());
        table.setItems(data);

        TableColumn<String, Player> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, Player> column2 = new TableColumn<>("Score");
        column2.setCellValueFactory(new PropertyValueFactory<>("score"));

        table.getColumns().add(column1);
        table.getColumns().add(column2);

        Region scorePanel = panel(
                vertical(
                        16,
                        horizontallyCentered(text("Scores", TextStyle.TITLE)),
                        spacer(16),
                        fillWith(table)
                )
        );

        this.getChildren().addAll(
                verticallyCentered(width(512, scorePanel)),
                backButton(actionEvent -> StageManager.goBackTo(new Main()))
        );
    }
}
