package message;

import messageloop.Message;

/**
 * QuestionChange
 */
public class OnAnswerCorrect extends Message {
    @Override
    public boolean repostable() {
        return true;
    }
}