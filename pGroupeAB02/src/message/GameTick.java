package message;

import messageloop.Message;

public class GameTick implements Message {

    @Override
    public boolean repostable() {
        return false;
    }

}