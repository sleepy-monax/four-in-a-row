package message;

import messageloop.Message;

public class NewClue implements Message {
    private final String clue;

    public NewClue(String clue) {
        this.clue = clue;
    }

    public String clue() {
        return clue;
    }

    public boolean repostable() {
        return false;
    }
}
