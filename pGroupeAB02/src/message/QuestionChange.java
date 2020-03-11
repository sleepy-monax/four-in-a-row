package message;

import messageloop.Message;

/**
 * QuestionChange
 */
public class QuestionChange implements Message {
    @Override
    public boolean repostable() {
        return true;
    }
}