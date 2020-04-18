package models.states;

import models.Player;

public abstract class GameState {
    public void onTick(double elapsed) {
    }

    public void onSwitchIn() {
    }

    public void onSwitchOut() {
    }

    public void selectTheme(String theme) {
    }

    public void answer(String answer) {
    }

    public void pass() {
    }

    public void quit(Player player) {
    }
}