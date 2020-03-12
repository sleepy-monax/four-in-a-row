package views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import message.OnCountDown;
import message.OnNewClue;
import models.Game;
import controls.AnswerField;
import controls.ClueStack;
import dialogs.YesNo;
import dialogs.YesNoDialog;

public class MainGame extends View {

    public MainGame(Game game) {
        this.setPadding(new Insets(0));

        Button quitButton = Widgets.makeButton("Exit");
        quitButton.setMinWidth(200);
        quitButton.setOnMouseClicked(event -> {
            if (new YesNoDialog("Quit the game", "Do you want to quit the game?\nAll progress will be lost!")
                    .show() == YesNo.YES) {
                game.finish();
            }
        });

        Label countdownLabel = new Label();

        VBox sidebar = new VBox();

        sidebar.setId("sidebar");
        sidebar.setMinWidth(220);
        sidebar.setPadding(new Insets(16));
        sidebar.getChildren().addAll(quitButton, countdownLabel);

        ClueStack clueStack = new ClueStack();
        clueStack.setPadding(new Insets(32));

        AnswerField answer = new AnswerField(game);
        HBox.setHgrow(answer, Priority.ALWAYS);

        BorderPane cluesAndAnswer = new BorderPane(clueStack, null, null, answer, null);

        HBox.setHgrow(cluesAndAnswer, Priority.ALWAYS);

        this.getChildren().add(new HBox(sidebar, cluesAndAnswer));

        game.getMessageLoop().registerNotifier(OnNewClue.class, message -> {
            clueStack.addClue(message.clue());
        });

        game.getMessageLoop().registerNotifier(OnCountDown.class, message -> {
            countdownLabel.setText(((int) message.time()) + "'");
        });
    }
}
