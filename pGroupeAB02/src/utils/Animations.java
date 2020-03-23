package utils;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {
    private Animations() {
    }

    public static Node fade(double from, double to, double duration, Node node) {
        return fade(from, to, duration, 0, null, node);
    }

    public static Node fade(Node node, double from, double to, double duration, double delay) {
        return fade(from, to, duration, delay, null, node);
    }

    public static Node fade(double from, double to, double duration, Runnable then, Node node) {
        return fade(from, to, duration, 0, then, node);
    }

    public static Node fade(double from, double to, double duration, double delay, Runnable then, Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(duration), node);

        fade.setDelay(Duration.seconds(delay));
        fade.setFromValue(from);
        fade.setToValue(to);

        if (then != null) {
            fade.setOnFinished(even -> then.run());
        }

        fade.play();

        return node;
    }

    public static Node scale(double from, double to, double duration, Node node) {
        return scale(node, from, to, duration, 0, null);
    }

    public static Node scale(double from, double to, double duration, double delay, Node node) {
        return scale(node, from, to, duration, delay, null);
    }

    public static Node scale(double from, double to, double duration, Runnable then, Node node) {
        return scale(node, from, to, duration, 0, then);
    }

    public static Node scale(Node node, double from, double to, double duration, double delay, Runnable then) {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(duration), node);

        scale.setDelay(Duration.seconds(delay));
        scale.setFromX(from);
        scale.setToX(to);
        scale.setFromY(from);
        scale.setToY(to);

        scale.setOnFinished(even -> {
            if (then != null) {
                then.run();
            }
        });

        scale.play();

        return node;
    }

    public static Node translateY(double from, double to, double duration, Node node) {
        return translateY(from, to, duration, 0, null, node);
    }

    public static Node translateY(Node node, double from, double to, double duration, double delay) {
        return translateY(from, to, duration, delay, null, node);
    }

    public static Node translateY(double from, double to, double duration, Runnable then, Node node) {
        return translateY(from, to, duration, 0, then, node);
    }

    public static Node translateY(double from, double to, double duration, double delay, Runnable then, Node node) {
        TranslateTransition offsetY = new TranslateTransition(Duration.seconds(duration), node);

        offsetY.setDelay(Duration.seconds(delay));
        offsetY.setFromY(from);
        offsetY.setToY(to);

        if (then != null) {
            offsetY.setOnFinished(even -> then.run());
        }

        offsetY.play();

        return node;
    }
}