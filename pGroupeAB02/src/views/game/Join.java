package views.game;

import javafx.scene.Node;
import views.TextStyle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import models.multiplayer.Multiplayer;
import utils.StageManager;
import utils.Icon;
import views.View;
import views.Widget;
import views.menu.Main;

import static views.Layout.*;
import static views.Widget.*;

public class Join extends View {
    public Join() {
        super(true);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(32));

        TextField hostAddressField = Widget.textField("localhost");
        TextField hostPortField = Widget.textField("" + Multiplayer.DEFAULT_PORT);
        TextField hostPasswordField = Widget.passwordField();

        Node joinButton = Widget.buttonWithIcon(Icon.PEOPLE, "Connect", mouseEvent -> {
            Multiplayer.join(hostAddressField.getText(), Integer.parseInt(hostPortField.getText()), hostPasswordField.getText());
        });

        Region loginPane = panel(
            vertical(
                16,
                horizontallyCentered(Widget.text("Join Game", TextStyle.TITLE)),
                spacer(16),
                text("Host adress:", TextStyle.LABEL),
                hostAddressField,
                text("Host port:", TextStyle.LABEL),
                hostPortField,
                text("Password:", TextStyle.LABEL),
                hostPasswordField,
                spacer(16),
                joinButton
            )
        );

        this.getChildren().addAll(
            verticallyCentered(width(512,loginPane)), 
            backButton(actionEvent -> StageManager.goBackTo(new Main())));
    }
}
