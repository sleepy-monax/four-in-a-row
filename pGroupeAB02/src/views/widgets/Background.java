package views.widgets;

import java.util.ArrayList;
import java.util.Random;

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
    private Pane spinner;

    private Pane particlesContainer;
    private ArrayList<Double> particlesSpeed;
    private ArrayList<Pane> particles;

    public Background() {
        gradient = new Pane();
        gradient.setId("background");
        gradient.setScaleX(1.25);
        gradient.setScaleY(1.25);
        gradient.setCacheHint(CacheHint.SPEED);

        getChildren().add(gradient);

        spinnerVisible = false;

        particlesContainer = new Pane();
        particlesContainer.setScaleX(1.25);
        particlesContainer.setScaleY(1.25);
        particlesContainer.setOpacity(0);
        particlesContainer.setCacheHint(CacheHint.SPEED);

        particles = new ArrayList<>();
        particlesSpeed = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < 300; i++) {
            Pane particle = new Pane();
            particle.setId("particle");

            particle.setMinWidth(64);
            particle.setMinHeight(64);

            particles.add(particle);
            particlesSpeed.add(rnd.nextDouble() + 0.5);
        }

        this.widthProperty().addListener((value, oldValue, newValue) -> {
            screenSizeChanged(newValue.doubleValue(), getHeight());
        });

        this.heightProperty().addListener((value, oldValue, newValue) -> {
            screenSizeChanged(getWidth(), newValue.doubleValue());
        });

        particlesContainer.getChildren().addAll(particles);

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

        getChildren().addAll(particlesContainer, spinner);
    }

    public void tick() {
        Random rnd = new Random();

        for (int i = 0; i < particles.size(); i++) {
            Pane pane = particles.get(i);

            double speed = 1
                    + Math.abs((pane.getTranslateY() / particlesContainer.getHeight()) * particlesSpeed.get(i));

            pane.setTranslateY(pane.getTranslateY() + speed);

            if (pane.getTranslateY() > particlesContainer.getHeight()) {
                pane.setTranslateX(rnd.nextDouble() * particlesContainer.getWidth());
                pane.setTranslateY(0); // rnd.nextDouble() * -128);
            }
        }

    }

    public void julien() {
        Pane julien = new Pane();
        julien.setId("julien");
        this.getChildren().add(julien);

        Animations.translateY(1080, 0, 0.25, 0, () -> {
            if (spinnerVisible) {
                Animations.fade(particlesContainer, 0, 1, 1, 0);
            }

            for (Pane pane : particles) {
                pane.setId("julien");
            }
        }, julien);
        Animations.translateY(julien, 0, 1080, 0.25, 1);
    }

    public void screenSizeChanged(double newWidth, double newHeight) {
        Random rnd = new Random();

        for (Pane pane : particles) {
            pane.setTranslateX(Math.pow(rnd.nextDouble(), 2) * newWidth);
            pane.setTranslateY(Math.pow(rnd.nextDouble(), 2) * newHeight);

            pane.setScaleX(newWidth / 1920);
            pane.setScaleY(newWidth / 1920);
        }

        if (spinnerVisible) {
            Animations.fade(particlesContainer, 0, 0.1, 1, 0);
        }
    }

    public void animate(double mouseX, double mouseY) {
        double x = (mouseX - getWidth() / 2) / getWidth();
        double y = (mouseY - getHeight() / 2) / getHeight();

        double offsetx = getWidth() * 0.25 * x * parallax;
        double offsety = getHeight() * 0.25 * y * parallax;

        gradient.setTranslateX(-offsetx);
        gradient.setTranslateY(-offsety);

        particlesContainer.setTranslateX(-offsetx * 0.70);
        particlesContainer.setTranslateY(-offsety * 0.70);

        spinner.setTranslateX(-offsetx * 0.50);
        spinner.setTranslateY(-offsety * 0.50);
    }

    public void showSpinner() {
        if (!spinnerVisible && spinner != null) {
            spinnerVisible = true;

            Animations.scale(spinner, 0, 5, 0.5);
            spinner.setOpacity(0.25);

            Animations.fade(0, 0.1, 0.5, particlesContainer);
        }
    }

    public void hideSpinner() {
        if (spinnerVisible && spinner != null) {
            spinnerVisible = false;

            Animations.scale(spinner, 5, 0, 0.5);
            Animations.fade(0.1, 0, 0.5, particlesContainer);
        }
    }

    public void hideSpinnerInstant() {
        if (spinnerVisible && spinner != null) {
            spinnerVisible = false;

            spinner.setScaleX(0);
            spinner.setScaleY(0);

            particlesContainer.setOpacity(0);
        }
    }

    public double getParallax() {
        return this.parallax;
    }

    public void setParallax(double parallax) {
        this.parallax = parallax;
    }
}