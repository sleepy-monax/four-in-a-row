package message;

import messageloop.Message;

/**
 * QuestionChange
 */
public class QuestionChange extends Message {
    @Override
    public boolean repostable() {
        return true;
    }
}