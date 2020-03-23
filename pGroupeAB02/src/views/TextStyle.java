package views;

public enum TextStyle {
    TITLE("title"),
    SUBTITLE("subtitle"),
    BODY("body"),
    LABEL("label");

    public final String styleClass;

    TextStyle(String styleClass) {
        this.styleClass = "text-" + styleClass;
    }
}
