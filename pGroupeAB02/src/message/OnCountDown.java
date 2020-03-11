package message;

import messageloop.Message;

public class OnCountDown extends Message {
    private final double time;

    public OnCountDown(double time) {
        this.time = time;
    }

    public double time() {
        return this.time;
    }

    @Override
    public boolean repostable() {
        return false;
    }
}