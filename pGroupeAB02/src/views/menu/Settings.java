package views.menu;

import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import utils.*;
import views.widgets.IconButton;
import views.widgets.Title;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import views.View;

public class Settings extends View {
    public Settings() {
        this.setPadding(new Insets(32));

        IconButton orbEffect = new IconButton(
                () -> AudioManager.isMuted() ? Icon.VOLUME_OFF : Icon.VOLUME_UP,
                event -> AudioManager.toggleMuted()
                );

        Slider sliderMusicVolume = new Slider();
        sliderMusicVolume.setMin(0);
        sliderMusicVolume.setMax(100);
        sliderMusicVolume.setValue(AudioManager.getMusicVolume() * 100);
        sliderMusicVolume.valueProperty().addListener(event -> {
            AudioManager.setMusicVolume(sliderMusicVolume.getValue() / 100.0);
        });
        HBox.setHgrow(sliderMusicVolume, Priority.ALWAYS);

        Slider sliderEffectVolume = new Slider();
        sliderEffectVolume.setMin(0);
        sliderEffectVolume.setMax(100);
        sliderEffectVolume.setValue(AudioManager.getEffectVolume() * 100);
        sliderEffectVolume.valueProperty().addListener(event -> {
            AudioManager.setEffectVolume(sliderEffectVolume.getValue() / 100.0);
        });
        HBox.setHgrow(sliderEffectVolume, Priority.ALWAYS);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(32));

        VBox menuContainer = new VBox(16);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setMaxWidth(512);
        menuContainer.getChildren().add(Widgets.makeLabel("Music"));
        menuContainer.getChildren().add(Layout.verticalyCentered(sliderMusicVolume));
        menuContainer.getChildren().add(Widgets.makeLabel("Effects"));
        menuContainer.getChildren().add(new HBox(16, Layout.verticalyCentered(sliderEffectVolume), orbEffect));

        Button backButton = Widgets.makeButton("Go back");
        backButton.setOnAction(actionEvent -> StageManager.switchView(new Main()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(new Title("Settings"), menuContainer, backButton);
    }
}
