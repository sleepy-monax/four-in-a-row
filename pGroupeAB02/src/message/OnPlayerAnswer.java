package message;

import messageloop.Message;

public class OnPlayerAnswer extends Message {
    private final String answer;

    public OnPlayerAnswer(String answer) {
        this.answer = answer;
    }

    public String answer() {
        return answer;
    }
}
