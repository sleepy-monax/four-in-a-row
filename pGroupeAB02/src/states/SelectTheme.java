package states;

import java.util.List;

import models.*;

public class SelectTheme implements GameState {
    public static final int THEME_COUNT = 3;

    private final Game game;
    private final List<String> themes;
    private final Player player;
    private final double timer;

    public SelectTheme(Game game, Player player) {
        this(game, player, Round.ROUND_TIME);
    }

    public SelectTheme(Game game, Player player, double timer) {
        this.game = game;
        this.themes = game.randomTheme(THEME_COUNT);
        this.player = player;
        this.timer = timer;
    }

    public List<String> themes() {
        return themes;
    }

    public void pick(String theme) {
        game.changeState(new Round(game, player, theme, game.getDeck().getQuestionsByTheme(theme)));
    }

    public void onTick(double elapsed) {
        // Do nothing...
    }
}