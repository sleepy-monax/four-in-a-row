package models.message;

import models.messageloop.Message;

public class OnSelectTheme extends Message {
    private final String[] themes;

    public OnSelectTheme(String[] themes) {
        this.themes = themes;
    }

    public String[] themes() {
        return this.themes;
    }
}