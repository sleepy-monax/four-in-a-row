package views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import message.OnCountDown;
import message.OnNewClue;
import message.OnAnswerCorrect;
import message.OnAnswerIncorrect;
import messageloop.Notifiable;
import models.Game;
import utils.ShakeTransition;
import controls.AnswerField;
import controls.ClueStack;
import dialogs.YesNo;
import dialogs.YesNoDialog;

public class MainGame extends View {

    private final Game game;

    private final ClueStack clueStack;
    private final Label countdown;
    private final AnswerField answer;

    public MainGame(Game game) {
        this.game = game;

        this.setPadding(new Insets(0));

        Button quitButton = Widgets.makeButton("Exit");
        quitButton.setMinWidth(200);
        quitButton.setOnMouseClicked(event -> {
            if (new YesNoDialog("Quit the game", "Do you want to quit the game?\nAll progress will be lost!")
                    .show() == YesNo.YES) {
                game.finish();
            }
        });

        countdown = new Label();

        VBox sidebar = new VBox();

        sidebar.setId("sidebar");
        sidebar.setMinWidth(220);
        sidebar.setPadding(new Insets(16));
        sidebar.getChildren().addAll(quitButton, countdown);

        clueStack = new ClueStack();
        clueStack.setPadding(new Insets(32));

        answer = new AnswerField(game);
        HBox.setHgrow(answer, Priority.ALWAYS);

        BorderPane cluesAndAnswer = new BorderPane(clueStack, null, null, answer, null);

        HBox.setHgrow(cluesAndAnswer, Priority.ALWAYS);

        this.getChildren().add(new HBox(sidebar, cluesAndAnswer));

    }

    private Notifiable onNewClueNotifier;
    private Notifiable onCountDownNotifier;
    private Notifiable onAnswerCorrect;
    private Notifiable onAnswerIncorrect;

    public void onSwitchIn() {
        onNewClueNotifier = game.getMessageLoop().registerNotifier(OnNewClue.class, message -> {
            clueStack.addClue(message.clue());
        });

        onCountDownNotifier = game.getMessageLoop().registerNotifier(OnCountDown.class, message -> {
            countdown.setText(((int) message.time()) + "'");
        });

        onAnswerCorrect = game.getMessageLoop().registerNotifier(OnAnswerCorrect.class, message -> {
            clueStack.clearClues();
        });

        onAnswerIncorrect = game.getMessageLoop().registerNotifier(OnAnswerIncorrect.class, message -> {
            ShakeTransition shake = new ShakeTransition(answer, Duration.seconds(1), 10, 10);

            shake.play();
        });
    }

    @Override
    public void onSwitchOut() {
        game.getMessageLoop().unregisterNotifier(onNewClueNotifier);
        game.getMessageLoop().unregisterNotifier(onCountDownNotifier);
        game.getMessageLoop().unregisterNotifier(onAnswerCorrect);
        game.getMessageLoop().unregisterNotifier(onAnswerIncorrect);
    }
}
