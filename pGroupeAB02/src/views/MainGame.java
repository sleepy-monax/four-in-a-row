package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.StageManager;
import controls.ClueStack;
import dialogs.YesNo;
import dialogs.YesNoDialog;

public class MainGame extends View {
    private Button btnQuitGame;
    private ClueStack clueStack;

    public Button getBtnQuitGame() {
        if (btnQuitGame == null) {
            btnQuitGame = Widgets.makeButton("Exit");
            btnQuitGame.setMinWidth(200);
            btnQuitGame.setOnMouseClicked(event -> {
                if (new YesNoDialog("Quit the game", "Do you want to quit the game?\nAll progress will be lost!")
                        .show() == YesNo.YES) {
                    StageManager.switchView(new MainMenu());
                }
            });
        }
        return btnQuitGame;
    }

    private Parent makeAnswerContainer() {
        TextField answer = Widgets.makeTextField("");
        StackPane.setAlignment(answer, Pos.CENTER);

        StackPane answerContainer = new StackPane(answer);
        answerContainer.setPadding(new Insets(0, 0, 0, 14));
        HBox.setHgrow(answerContainer, Priority.ALWAYS);

        Pane buzzer = Widgets.makeBuzzer();
        buzzer.setOnMouseClicked(event -> clueStack.addClue("Hello, world!"));

        HBox answerAndBuzzerContainer = new HBox(answerContainer, buzzer);
        answerAndBuzzerContainer.setMaxHeight(48);

        return answerAndBuzzerContainer;
    }

    private Parent makeSideBar() {
        VBox sidebar = new VBox();

        sidebar.setId("sidebar");
        sidebar.setMinWidth(220);
        sidebar.setPadding(new Insets(16));
        sidebar.getChildren().addAll(getBtnQuitGame());

        return sidebar;
    }

    public MainGame() {
        this.setPadding(new Insets(0));

        Parent sidebar = makeSideBar();

        clueStack = new ClueStack();
        clueStack.setPadding(new Insets(32));

        Parent answer = makeAnswerContainer();
        HBox.setHgrow(answer, Priority.ALWAYS);

        BorderPane cluesAndAnswer = new BorderPane(clueStack, null, null, answer, null);

        HBox.setHgrow(cluesAndAnswer, Priority.ALWAYS);

        this.getChildren().add(new HBox(sidebar, cluesAndAnswer));
    }
}
