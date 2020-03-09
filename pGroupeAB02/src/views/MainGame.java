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
import javafx.scene.text.TextAlignment;
import utils.StageManager;

import dialogs.YesNo;
import dialogs.YesNoDialog;

public class MainGame extends View {
    private Button btnQuitGame;

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

    private Parent makeClues() {
        VBox clues = new VBox();

        StackPane card0 = new StackPane();
        card0.getStyleClass().add("card");
        card0.setRotate(1);

        StackPane card1 = new StackPane();
        card1.getStyleClass().add("card");
        card1.setRotate(-1.25);

        StackPane card2 = new StackPane();
        card2.getStyleClass().add("card");
        card2.setRotate(1.2);

        clues.getChildren().addAll(card0, card1, card2);

        Label text2 = new Label(
                "He designed the Automatic Computing Engine, which was one of the first designs for a stored-program computer.");

        text2.getStyleClass().add("card-body");
        text2.setWrapText(true);
        text2.setAlignment(Pos.CENTER);
        text2.setTextAlignment(TextAlignment.CENTER);

        card2.getChildren().add(text2);

        return clues;
    }

    public MainGame() {
        this.setPadding(new Insets(0));

        Parent answer = makeAnswerContainer();
        HBox.setHgrow(answer, Priority.ALWAYS);

        Parent sidebar = makeSideBar();

        Parent clues = makeClues();
        VBox.setVgrow(clues, Priority.ALWAYS);

        VBox cluesAndAnswer = new VBox(16, new StackPane(clues), answer);
        VBox.setVgrow(cluesAndAnswer, Priority.ALWAYS);

        this.getChildren().add(new HBox(16, sidebar, cluesAndAnswer));
    }
}
