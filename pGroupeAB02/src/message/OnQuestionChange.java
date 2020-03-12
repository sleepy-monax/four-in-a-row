package message;

import messageloop.Message;

/**
 * QuestionChange
 */
public class OnQuestionChange extends Message {
    @Override
    public boolean repostable() {
        return true;
    }
}