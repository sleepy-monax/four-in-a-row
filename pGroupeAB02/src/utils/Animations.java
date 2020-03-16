package utils;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {
    private Animations() {
    }

    public static void fade(Node node, double from, double to, double duration) {
        fade(node, from, to, duration, 0, null);
    }

    public static void fade(Node node, double from, double to, double duration, double delay) {
        fade(node, from, to, duration, delay, null);
    }

    public static void fade(Node node, double from, double to, double duration, Runnable then) {
        fade(node, from, to, duration, 0, then);
    }

    public static void fade(Node node, double from, double to, double duration, double delay, Runnable then) {
        FadeTransition fade = new FadeTransition(Duration.seconds(duration), node);

        fade.setDelay(Duration.seconds(delay));
        fade.setFromValue(from);
        fade.setToValue(to);

        if (then != null) {
            fade.setOnFinished(even -> then.run());
        }

        fade.play();
    }

    public static void scale(Node node, double from, double to, double duration) {
        scale(node, from, to, duration, 0, null);
    }

    public static void scale(Node node, double from, double to, double duration, double delay) {
        scale(node, from, to, duration, delay, null);
    }

    public static void scale(Node node, double from, double to, double duration, Runnable then) {
        scale(node, from, to, duration, 0, then);
    }

    public static void scale(Node node, double from, double to, double duration, double delay, Runnable then) {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(duration), node);

        scale.setDelay(Duration.seconds(delay));
        scale.setFromX(from);
        scale.setToX(to);
        scale.setFromY(from);
        scale.setToY(to);

        if (then != null) {
            scale.setOnFinished(even -> then.run());
        }

        scale.play();
    }

    public static void offsetY(Node node, double from, double to, double duration) {
        offsetY(node, from, to, duration, 0, null);
    }

    public static void offsetY(Node node, double from, double to, double duration, double delay) {
        offsetY(node, from, to, duration, delay, null);
    }

    public static void offsetY(Node node, double from, double to, double duration, Runnable then) {
        offsetY(node, from, to, duration, 0, then);
    }

    public static void offsetY(Node node, double from, double to, double duration, double delay, Runnable then) {
        TranslateTransition offsetY = new TranslateTransition(Duration.seconds(duration), node);

        offsetY.setDelay(Duration.seconds(delay));
        offsetY.setFromY(from);
        offsetY.setToY(to);

        if (then != null) {
            offsetY.setOnFinished(even -> then.run());
        }

        offsetY.play();
    }
}