package message;

import messageloop.Message;

public class OnNewClue extends Message {
    private final String clue;

    public OnNewClue(String clue) {
        this.clue = clue;
    }

    public String clue() {
        return clue;
    }

    public boolean repostable() {
        return false;
    }
}
