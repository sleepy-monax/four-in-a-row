package views.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import models.multiplayer.Multiplayer;
import utils.Icon;
import utils.StageManager;
import views.TextStyle;
import views.View;
import views.Widget;
import views.menu.Main;

import static views.Layout.*;
import static views.Widget.*;

public class Host extends View {

    public Host() {
        super(true);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(32));

        TextField gamePortField = Widget.textField("" + Multiplayer.DEFAULT_PORT);
        TextField gamePasswordField = Widget.passwordField();

        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton easyButton = new RadioButton("Easy");
        RadioButton mediumButton = new RadioButton("Medium");
        RadioButton hardButton = new RadioButton("Hard");

        easyButton.setToggleGroup(toggleGroup);
        mediumButton.setToggleGroup(toggleGroup);
        hardButton.setToggleGroup(toggleGroup);

        mediumButton.setSelected(true);

        Node createButton = Widget.buttonWithIcon(Icon.PEOPLE, "Create Game", mouseEvent -> {
            Multiplayer.host(Integer.parseInt(gamePortField.getText()), gamePasswordField.getText());
        });

        Region loginPane = panel(
            vertical(
                16,
                horizontallyCentered(Widget.text("Host Game", TextStyle.TITLE)),
                spacer(16),
                text("Game port:", TextStyle.LABEL),
                gamePortField,
                text("Game Password:", TextStyle.LABEL),
                gamePasswordField,
                text("Dificulty:", TextStyle.LABEL),
                easyButton,
                mediumButton,
                hardButton,
                spacer(16),
                createButton
            )
        );

        this.getChildren().addAll(
            verticallyCentered(width(512,loginPane)),
            backButton(actionEvent -> StageManager.switchView(new Main())));
    }

}