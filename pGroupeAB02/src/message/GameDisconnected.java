package message;

import messageloop.Message;

public class GameDisconnected implements Message {

    @Override
    public boolean repostable() {
        return true;
    }
}