package views.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import utils.AudioManager;
import utils.Icon;
import utils.StageManager;
import views.Layout;
import views.View;
import views.Widget;
import views.widgets.Title;

public class Settings extends View {
    public Settings() {
        this.setPadding(new Insets(32));
        this.setAlignment(Pos.CENTER);

        Node menu = Layout.width(
                512,
                Layout.verticallyCentered(
                        Layout.vertical(
                                16,
                                Layout.verticallyCentered(
                                        Widget.label("Audio")
                                ),
                                Layout.horizontal(
                                        16,
                                        Layout.verticallyCentered(
                                                Widget.label("Muted: ")
                                        ),
                                        Widget.iconButton(
                                                () -> AudioManager.isMuted() ? Icon.VOLUME_OFF : Icon.VOLUME_UP,
                                                event -> AudioManager.toggleMuted()
                                        )
                                ),
                                Layout.horizontal(
                                        16,
                                        Layout.verticallyCentered(
                                                Widget.label("Music: ")
                                        ),
                                        Layout.fill(
                                                Layout.verticallyCentered(
                                                        Widget.slider(
                                                                0,
                                                                1,
                                                                AudioManager::getMusicVolume,
                                                                AudioManager::setMusicVolume
                                                        )
                                                )
                                        )
                                ),
                                Layout.horizontal(
                                        16,
                                        Layout.verticallyCentered(
                                                Widget.label("Effects: ")
                                        ),
                                        Layout.fill(
                                                Layout.verticallyCentered(
                                                        Widget.slider(
                                                                0,
                                                                1,
                                                                AudioManager::getEffectVolume,
                                                                AudioManager::setEffectVolume
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        Button backButton = Widget.button("Go back", event -> StageManager.switchView(new Main()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        this.getChildren().addAll(new Title("Settings"), menu, backButton);
    }
}
