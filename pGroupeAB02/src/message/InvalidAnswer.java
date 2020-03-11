package message;

import messageloop.Message;

public class InvalidAnswer extends Message {
    public boolean repostable() {
        return false;
    }
}