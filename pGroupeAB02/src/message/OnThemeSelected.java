package message;

import messageloop.Message;

public class OnThemeSelected extends Message {
    private final String theme;

    public OnThemeSelected(String theme) {
        this.theme = theme;
    }

    public String theme() {
        return this.theme;
    }
}