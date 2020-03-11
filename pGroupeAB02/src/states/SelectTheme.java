package states;

import models.*;

public class SelectTheme extends GameState {
    public static final int THEME_COUNT = 3;

    private final Game game;
    private final String[] themes;
    private final Player player;
    private final double timer;

    public SelectTheme(Game game, Player player) {
        this(game, player, Round.ROUND_TIME);
    }

    public SelectTheme(Game game, Player player, double timer) {
        this.game = game;
        this.themes = game.getDeck().getRandomThemes(THEME_COUNT);
        this.player = player;
        this.timer = timer;
    }

    public void pickTheme(String theme) {
        game.changeState(new Round(game, player, theme, game.getDeck().getQuestionsByTheme(theme), timer));
    }

    @Override
    public void onSwitchIn() {
        game.getMessageLoop().post(new message.OnSelectTheme(themes));
    }
}