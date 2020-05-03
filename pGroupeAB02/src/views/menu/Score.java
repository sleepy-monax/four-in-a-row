package views.menu;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import models.Player;
import models.scores.ListScore;
import utils.StageManager;
import views.TextStyle;
import views.View;

import static views.Layout.*;
import static views.Widget.*;

import java.util.Comparator;

public class Score extends View {
    public Score() {
        super(true);

        TableView table = new TableView();

        ObservableList<models.scores.Score> data = FXCollections.observableArrayList(ListScore.get().getScores());


        Comparator< models.scores.Score> comparator = Comparator.comparingInt(models.scores.Score::getScore); 
        FXCollections.sort(data, comparator);
        FXCollections.reverse(data);
        

        table.setItems(data);

        TableColumn<Score, String> column1 = new TableColumn<>("Place");
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Score,String>,ObservableValue<String>>(){
        
                @Override
                public ObservableValue<String> call(CellDataFeatures<Score, String> param) {
                        return  new ReadOnlyStringWrapper("" + (data.indexOf(param.getValue()) + 1)) ;
                }
        });

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
                verticallyCentered(width(512, scorePanel)),
                backButton(actionEvent -> StageManager.goBackTo(new Main()))
        );
    }
}
