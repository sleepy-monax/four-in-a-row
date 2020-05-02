package utils;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.util.Duration;

public class ShakeTransition extends Transition {

    private final Node node;
    private final Duration duration;
    private final double offset;
    private final int steps;
    private final DoubleProperty transitionValue = new SimpleDoubleProperty();
    private boolean wasNodeCached = false;
    private CacheHint howNodeWasChached = CacheHint.DEFAULT;
    private Timeline timeline;
    private final Interpolator interpolation = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);

    public ShakeTransition(Node node, Duration duration, double offset, int steps) {
        this.node = node;
        this.duration = duration;
        this.offset = offset;
        this.steps = steps;

        statusProperty().addListener((ov, t, newStatus) -> {
            switch (newStatus) {
                case RUNNING:
                    wasNodeCached = node.isCache();
                    howNodeWasChached = node.getCacheHint();

                    node.setCache(true);
                    node.setCacheHint(CacheHint.SPEED);
                    break;
                default:
                    node.setCache(wasNodeCached);
                    node.setCacheHint(howNodeWasChached);
                    break;
            }
        });
    }

    @Override
    protected void interpolate(double d) {
        timeline.playFrom(Duration.seconds(d));
        timeline.stop();
    }

    @Override
    public void play() {
        double step = duration.toSeconds() / steps;

        timeline = new Timeline();

        for (int i = 0; i < steps; i++) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(step * i),
                    new KeyValue(transitionValue, offset * Math.pow(-1, i), interpolation)));
        }

        timeline.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(step * steps), new KeyValue(transitionValue, 0, interpolation)));

        transitionValue.addListener((ob, n, n1) -> node.setTranslateX(n1.doubleValue()));

        setCycleDuration(duration);

        super.play();
    }
}