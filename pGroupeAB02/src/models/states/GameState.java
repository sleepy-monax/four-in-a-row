package models.states;

public abstract class GameState {
    public void onTick(double elapsed) {
    }

    public void onSwitchIn() {
    }

    public void onSwitchOut() {
    }
}