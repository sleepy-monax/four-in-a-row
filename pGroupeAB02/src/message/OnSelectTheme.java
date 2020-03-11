package message;

import java.util.List;

import messageloop.Message;

public class OnSelectTheme extends Message {
    private final List<String> themes;

    public OnSelectTheme(List<String> themes) {
        this.themes = themes;
    }

    public List<String> themes() {
        return this.themes;
    }

}