package views;

import controller.AudioController;
import controls.AnswerField;
import controls.ClueStack;
import controls.Countdown;
import dialogs.YesNo;
import dialogs.YesNoDialog;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.util.Duration;
import message.*;
import messageloop.Notifiable;
import models.Game;
import utils.ShakeTransition;

public class MainGame extends View {

    private final Game game;

    private final ClueStack clueStack;
    private final AnswerField answer;
    private final Countdown countdown;
    private Notifiable onNewClueNotifier;
    private Notifiable onCountdownNotifier;
    private Notifiable onAnswerCorrect;
    private Notifiable onAnswerIncorrect;
    private Notifiable onRoundFinish;
    public MainGame(Game game) {
        this.game = game;

        this.setPadding(new Insets(0));

        Pane quitButton = Widgets.makeOrbButton(Icon.CLOSE);
        quitButton.setMinWidth(200);
        quitButton.setOnMouseClicked(event -> {
            if (new YesNoDialog("Quit the game", "Do you want to quit the game?\nAll progress will be lost!")
                    .show() == YesNo.YES) {
                game.finish();
            }
        });

        countdown = new Countdown();

        BorderPane sidebar = new BorderPane();

        sidebar.setId("sidebar");
        sidebar.setMinWidth(220);
        sidebar.setPadding(new Insets(16));

        sidebar.setTop(new VBox(16, new StackPane(quitButton), new StackPane(Widgets.makeOrbButton(Icon.SKIP_NEXT))));
        sidebar.setBottom(new StackPane(countdown));

        clueStack = new ClueStack();
        clueStack.setPadding(new Insets(32));

        answer = new AnswerField();
        answer.setOnAnswer(answer -> game.answer(answer));
        HBox.setHgrow(answer, Priority.ALWAYS);

        BorderPane cluesAndAnswer = new BorderPane(clueStack, null, null, answer, null);

        HBox.setHgrow(cluesAndAnswer, Priority.ALWAYS);

        this.getChildren().add(new HBox(sidebar, cluesAndAnswer));

    }

    public void onSwitchIn() {
        onNewClueNotifier = game.getMessageLoop().registerNotifier(OnNewClue.class, message -> {
            clueStack.addClue(message.clue());
        });

        onCountdownNotifier = game.getMessageLoop().registerNotifier(OnCountDown.class, message -> {
            countdown.update(message.time());

            if (message.time() == 5) {
                AudioController.playEffect("assets/round-timer.wav");
            }

            if (message.time() == 1) {
                AudioController.playEffect("assets/round-end.wav");
            }
        });

        onAnswerCorrect = game.getMessageLoop().registerNotifier(OnAnswerCorrect.class, message -> {
            AudioController.playEffect("assets/correct.wav");

            clueStack.clearClues();
            answer.clear();
        });

        onAnswerIncorrect = game.getMessageLoop().registerNotifier(OnAnswerIncorrect.class, message -> {
            AudioController.playEffect("assets/wrong.wav");

            ShakeTransition shake = new ShakeTransition(answer, Duration.seconds(0.5), 16, 3);
            shake.play();
        });

        onRoundFinish = game.getMessageLoop().registerNotifier(OnRoundFinish.class, message -> {
            AudioController.stop();
        });
    }

    @Override
    public void onSwitchOut() {
        game.getMessageLoop().unregisterNotifier(onNewClueNotifier);
        game.getMessageLoop().unregisterNotifier(onCountdownNotifier);
        game.getMessageLoop().unregisterNotifier(onAnswerCorrect);
        game.getMessageLoop().unregisterNotifier(onAnswerIncorrect);
        game.getMessageLoop().unregisterNotifier(onRoundFinish);
    }
}
