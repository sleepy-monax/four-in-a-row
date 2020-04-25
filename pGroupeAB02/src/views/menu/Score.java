package views.menu;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import models.Player;
import utils.StageManager;
import views.TextStyle;
import views.View;

import static views.Widget.*;
import static views.Layout.*;

public class Score extends View {
    public Score() {
        super(true);
        
        TableView table = new TableView();

        TableColumn<String, Player> column1 = new TableColumn<>("Position");
        column1.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<String, Player> column2 = new TableColumn<>("Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, Player> column3 = new TableColumn<>("Score");
        column3.setCellValueFactory(new PropertyValueFactory<>("score"));

        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);

        Region scorePanel = panel(
            vertical(
                16,
                horizontallyCentered(text("Scores", TextStyle.TITLE)),
                spacer(16),
                fillWith(table)
            )
        );

        this.getChildren().addAll(
            verticallyCentered(width(512,scorePanel)),
            backButton(actionEvent -> StageManager.switchView(new Main()))
        );
    }
}
