package models.states;

import models.Game;
import models.Player;
import models.Question;
import models.message.*;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Round extends GameState {
    public static final double ROUND_TIME = 60.0;
    public static final double CLUE_TIME = 5.0;

    private final Game game;
    private final Player player;
    private final LinkedList<Question> questions;

    private double timer;
    private double currentQuestionTime;
    private double lastClueTime;
    private Question currentQuestion;

    public Round(Game game, Player player, List<Question> questions, double timer) {
        this(game, player, questions);
        this.timer = timer;
    }

    public Round(Game game, Player player, List<Question> questions) {
        this.game = game;
        this.player = player;
        this.questions = new LinkedList<>(questions);
    }

    @Override
    public void onSwitchIn() {
        nextQuestion();
        player.setHasPlayed();
        game.getMessageLoop().post(new OnCountDown((int) (ROUND_TIME)));
    }

    public void onTick(double elapsed) {
        if ((int) (timer - elapsed) != (int) (timer)) {
            game.getMessageLoop().post(new OnCountDown((int) (timer - elapsed)));
        }

        timer -= elapsed;

        currentQuestionTime += elapsed;

        if (currentQuestionTime - lastClueTime >= CLUE_TIME) {
            sendClue();
        }

        if (timer <= 0) {
            game.getMessageLoop().post(new OnRoundFinish());
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

            game.getMessageLoop().post(new OnNewClue(player, clue));
        }

        lastClueTime = currentQuestionTime;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    static int levenshteinDistance(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    private boolean AreAnswersEquals(String lhs, String rhs) {
        lhs = Normalizer.normalize(lhs.toLowerCase().replace(" ", ""), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        rhs = Normalizer.normalize(rhs.toLowerCase().replace(" ", ""), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return levenshteinDistance(lhs, rhs) <= game.getDifficulty().getAnswerTolerance();
    }

    public void answer(String answer) {
        game.getMessageLoop().post(new OnPlayerAnswer(player, answer));

        if (AreAnswersEquals(currentQuestion.getAnswer(), answer.toLowerCase())) {
            game.getMessageLoop().post(new OnAnswerCorrect());
            player.answerCorrect();
            nextQuestion();
        } else {
            game.getMessageLoop().post(new OnAnswerIncorrect());
            player.failed();
        }
    }

    @Override
    public void quit(Player player) {
        if (player.equals(this.player)) {
            game.nextPlayer();
        }
    }

    public void pass() {
        currentQuestion = null;

        game.getMessageLoop().post(new OnQuestionPassed());
        nextQuestion();
    }
}