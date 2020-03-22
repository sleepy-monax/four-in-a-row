package views.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import utils.SettingsManager;
import utils.Icon;
import utils.StageManager;
import views.View;
import views.widgets.Title;
import static views.Widget.*;
import static views.Layout.*;

public class Settings extends View {
    public Settings() {
        this.setPadding(new Insets(32));
        this.setAlignment(Pos.CENTER);

        Node menu = width(
                512,
                verticallyCentered(
                        vertical(
                                16,
                                verticallyCentered(
                                        label("Username")
                                ),
                                textField(
                                        ()->SettingsManager.get().getPlayerName(),
                                        newName -> SettingsManager.get().setPlayerName(newName)
                                ),
                                verticallyCentered(
                                        label("Audio")
                                ),
                                horizontal(
                                        16,
                                        verticallyCentered(
                                                width(96, label("Muted: "))
                                        ),
                                        iconButton(
                                                () -> SettingsManager.get().getAudioMuted() ? Icon.VOLUME_OFF : Icon.VOLUME_UP,
                                                event -> SettingsManager.get().toggleAudioMuted()
                                        )
                                ),
                                horizontal(
                                        16,
                                        verticallyCentered(
                                                width(96, label("Music: "))
                                        ),
                                        fill(
                                                verticallyCentered(
                                                        slider(
                                                                0,
                                                                1,
                                                                () -> SettingsManager.get().getAudioMusicVolume(),
                                                                value -> SettingsManager.get().setAudioMusicVolume(value)
                                                        )
                                                )
                                        )
                                ),
                                horizontal(
                                        16,
                                        verticallyCentered(
                                                width(96, label("Effects: "))
                                        ),
                                        fill(
                                                verticallyCentered(
                                                        slider(
                                                                0,
                                                                1,
                                                                () -> SettingsManager.get().getAudioEffectVolume(),
                                                                value -> SettingsManager.get().setAudioEffectVolume(value)
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        Button backButton = button("Go back", event -> {
                SettingsManager.save();
                StageManager.switchView(new Main());
        });
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(new Title("Settings"), menu, backButton);
    }
}
