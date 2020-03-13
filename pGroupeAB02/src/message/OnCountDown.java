package message;

import messageloop.Message;

public class OnCountDown extends Message {
    private final int time;

    public OnCountDown(int time) {
        this.time = time;
    }

    public int time() {
        return this.time;
    }
}