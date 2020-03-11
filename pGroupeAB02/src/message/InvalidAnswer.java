package message;

import messageloop.Message;

public class InvalidAnswer implements Message {
    public boolean repostable() {
        return false;
    }
}