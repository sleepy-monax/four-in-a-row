package views.widgets;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.scene.CacheHint;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import utils.Animations;

public class Background extends StackPane {
    private boolean spinnerVisible;
    private double parallax = 0;

    private Pane gradient;
    private Pane glitter;
    private Pane spinner;

    public Background() {
        gradient = new Pane();
        gradient.setId("background");
        gradient.setScaleX(1.25);
        gradient.setScaleY(1.25);
        gradient.setCacheHint(CacheHint.SPEED);

        getChildren().add(gradient);

        spinnerVisible = false;

        glitter = new Pane();
        glitter.setId("glitter");
        glitter.setScaleX(1.25);
        glitter.setScaleY(1.25);
        glitter.setOpacity(0);
        glitter.setCacheHint(CacheHint.SPEED);

        spinner = new Pane();
        spinner.setId("wheel");
        spinner.setMinHeight(350);
        spinner.setMinWidth(350);
        spinner.setMaxHeight(350);
        spinner.setMaxWidth(350);
        spinner.setScaleX(0);
        spinner.setScaleY(0);
        spinner.setOpacity(0.25);
        spinner.setCacheHint(CacheHint.SPEED);

        RotateTransition animation = new RotateTransition(Duration.seconds(16), spinner);
        animation.setByAngle(360);
        animation.setCycleCount(Transition.INDEFINITE);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.play();

        getChildren().addAll(glitter, spinner);
    }

    public void animate(double mouseX, double mouseY) {
        double x = (mouseX - getWidth() / 2) / getWidth();
        double y = (mouseY - getHeight() / 2) / getHeight();

        double offsetx = getWidth() * 0.25 * x * parallax;
        double offsety = getHeight() * 0.25 * y * parallax;

        gradient.setTranslateX(-offsetx);
        gradient.setTranslateY(-offsety);

        glitter.setTranslateX(-offsetx * 0.75);
        glitter.setTranslateY(-offsety * 0.75);

        spinner.setTranslateX(-offsetx * 0.5);
        spinner.setTranslateY(-offsety * 0.5);
    }

    public void showSpinner() {
        if (!spinnerVisible && spinner != null) {
            spinnerVisible = true;

            Animations.scale(0, 5, 0.5, spinner);
            Animations.fade(0, 0.25, 0.5, glitter);
        }
    }

    public void hideSpinner() {
        if (spinnerVisible && spinner != null) {
            spinnerVisible = false;

            Animations.scale(5, 0, 0.5, spinner);
            Animations.fade(0.25, 0, 0.5, glitter);
        }
    }

    public double getParallax() {
        return this.parallax;
    }

    public void setParallax(double parallax) {
        this.parallax = parallax;
    }
}