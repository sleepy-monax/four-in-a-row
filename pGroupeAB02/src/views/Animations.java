package views;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
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

        scale.setOnFinished(even -> {
            if (then != null) {
                then.run();
            }
        });

        scale.play();

    }

    public static void translateY(Node node, double from, double to, double duration) {
        translateY(node, from, to, duration, 0, null);
    }

    public static void translateY(Node node, double from, double to, double duration, double delay) {
        translateY(node, from, to, duration, delay, null);
    }

    public static void translateY(Node node, double from, double to, double duration, Runnable then) {
        translateY(node, from, to, duration, 0, then);
    }

    public static void translateY(Node node, double from, double to, double duration, double delay, Runnable then) {
        TranslateTransition offsetY = new TranslateTransition(Duration.seconds(duration), node);

        offsetY.setDelay(Duration.seconds(delay));
        offsetY.setFromY(from);
        offsetY.setToY(to);

        if (then != null) {
            offsetY.setOnFinished(even -> then.run());
        }

        offsetY.play();

    }
    
    public static void translateX(Node node, double from, double to, double duration) {
        translateX(node, from, to, duration, 0, null);
    }

    public static void translateX(Node node, double from, double to, double duration, double delay) {
        translateX(node, from, to, duration, delay, null);
    }

    public static void translateX(Node node, double from, double to, double duration, Runnable then) {
         translateX(node, from, to, duration, 0, then);
    }

    public static void translateX(Node node, double from, double to, double duration, double delay, Runnable then) {
        TranslateTransition offsetX = new TranslateTransition(Duration.seconds(duration), node);

        offsetX.setDelay(Duration.seconds(delay));
        offsetX.setFromX(from);
        offsetX.setToX(to);
        offsetX.setInterpolator(Interpolator.EASE_BOTH);

        if (then != null) {
            offsetX.setOnFinished(even -> then.run());
        }

        offsetX.play();
    }
}