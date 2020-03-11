package message;

import messageloop.Message;

public class StateChanged<GameStateType> implements Message {

    private final GameStateType state;

    public StateChanged(GameStateType state) {
        this.state = state;
    }

    public GameStateType state() {
        return state;
    }

    public boolean repostable() {
        return true;
    }
}