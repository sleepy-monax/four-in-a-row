package states;

import java.util.LinkedList;
import java.util.List;

import message.*;
import models.*;

public class Round extends GameState {
    public static final double ROUND_TIME = 60.0;
    public static final double CLUE_TIME = 5.0;

    private final Game game;
    private final Player player;
    private final String theme;
    private final LinkedList<Question> questions;

    private double timer;
    private double currentQuestionTime;
    private double lastclueTime;
    private Question currentQuestion;

    public Round(Game game, Player player, String theme, List<Question> questions, double timer) {
        this(game, player, theme, questions);
        this.timer = timer;
    }

    public Round(Game game, Player player, String theme, List<Question> questions) {
        this.game = game;
        this.player = player;
        this.theme = theme;
        this.questions = new LinkedList<>(questions);
    }

    @Override
    public void onSwitchIn() {
        nextQuestion();
    }

    public void onTick(double elapsed) {
        timer -= elapsed;
        currentQuestionTime += elapsed;

        if (currentQuestionTime - lastclueTime >= CLUE_TIME) {
            sendClue();
        }

        if (timer <= 0) {
            player.played();
            game.nextPlayer();
        }
    }

    public void nextQuestion() {
        if (questions.size() > 0) {
            currentQuestion = questions.removeFirst();
            currentQuestionTime = 0;

            sendClue();
        } else {
            game.changeState(new SelectTheme(game, player, timer));
        }
    }

    private void sendClue() {
        int clueIndex = (int) (currentQuestionTime / CLUE_TIME);

        if (clueIndex < currentQuestion.getClues().size()) {
            String clue = currentQuestion.getClues().get(clueIndex);

            game.getMessageLoop().post(new OnNewClue(clue));
        }

        lastclueTime = currentQuestionTime;
    }

    public void answer(String answer) {
        if (currentQuestion.getAnswer().equals(answer)) {
            player.answerCorrect();
            nextQuestion();
        } else {
            game.getMessageLoop().post(new InvalidAnswer());
            player.failled();
        }
    }

    public void pass() {
        questions.addLast(currentQuestion);
        currentQuestion = questions.removeFirst();

        game.getMessageLoop().post(new QuestionChange());
    }

}