package message;

import messageloop.Message;

public class OnGameTick extends Message {
    @Override
    public boolean repostable() {
        return false;
    }
}