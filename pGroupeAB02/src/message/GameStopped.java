package message;

import messageloop.Message;

public class GameStopped implements Message {

    @Override
    public boolean repostable() {
        return true;
    }

}