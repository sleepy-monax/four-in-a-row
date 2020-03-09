package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.StageManager;

import java.util.Optional;

public class MainGame extends View {
    private Button btnQuitGame;

    private Parent makeAnswerContainer() {
        TextField answer = Widgets.makeTextField("");
        StackPane.setAlignment(answer, Pos.CENTER);

        StackPane answerContainer = new StackPane(answer);
        answerContainer.setPadding(new Insets(0, 0, 0, 14));
        HBox.setHgrow(answerContainer, Priority.ALWAYS);

        Pane buzzer = Widgets.makeBuzzer();

        HBox answerAndBuzzerContainer = new HBox(answerContainer, buzzer);
        answerAndBuzzerContainer.setMaxHeight(48);

        return answerAndBuzzerContainer;
    }

    private Parent makeSideBar() {
        VBox sidebar = new VBox();
        sidebar.getChildren().addAll(getBtnQuitGame());

        sidebar.setId("sidebar");
        sidebar.setMinWidth(220);

        return sidebar;
    }

    private Parent makeClues() {
        VBox clues = new VBox();

        Label text = new Label(
                "He designed the Automatic Computing Engine, which was one of the first designs for a stored-program computer.");
        text.setWrapText(true);
        text.getStyleClass().add("clue");
        text.setMaxWidth(512);
        text.setAlignment(Pos.CENTER);

        clues.getChildren().add(text);

        return clues;
    }

    public MainGame() {
        this.setPadding(new Insets(0));

        Parent answer = makeAnswerContainer();
        HBox.setHgrow(answer, Priority.ALWAYS);

        Parent sidebar = makeSideBar();

        Parent clues = makeClues();
        VBox.setVgrow(clues, Priority.ALWAYS);

        VBox cluesAndAnswer = new VBox(16, clues, answer);
        HBox.setHgrow(cluesAndAnswer, Priority.ALWAYS);

        this.getChildren().add(new HBox(16, sidebar, cluesAndAnswer));
    }

    public Button getBtnQuitGame() {
        if (btnQuitGame == null){
            btnQuitGame = Widgets.makeButton("Exit");
            btnQuitGame.setMinWidth(200);
            btnQuitGame.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quit the game?");
                alert.setHeaderText("Do you want to quit the game? All progress will not saved");
                alert.setContentText("Are you ok with this?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    StageManager.switchView(new MainMenu());
                }
            });
        }
        return btnQuitGame;
    }
}