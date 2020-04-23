package views.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import models.Deck;
import models.Users.User;
import utils.Icon;
import utils.StageManager;
import views.TextStyle;
import views.View;
import views.dialogs.InfoDialog;

import static views.Layout.*;
import static views.Widget.*;

public class AdminConnect extends View {
    private static User ADMIN = new User("admin", "helha");

    private void login(String username, String password)
    {
        User user = new User(username, password);

        if (user.equals(ADMIN)) {
            StageManager.switchView(new Editor(Deck.get()));
        } else {
            new InfoDialog("Error", "Wrong login or password").show();
        }
    }

    public AdminConnect() {
        setPadding(new Insets(32));

        TextField userNameFiled = textField();

        TextField passwordField = passwordField();

        Region connectButton = buttonWithIcon(
            Icon.CHECK,
            "Connect",
            event -> login(userNameFiled.getText(), passwordField.getText())
        );

        Region loginPane = panel(
            vertical(
                16,
                horizontallyCentered(text("Connect", TextStyle.TITLE)),
                spacer(16),
                text("Username:", TextStyle.LABEL),
                userNameFiled,
                text("Password:", TextStyle.LABEL),
                passwordField,
                spacer(16),
                horizontallyCentered(width(320, connectButton))
            )
        );

        Button backButton = button("Go back", actionEvent -> StageManager.switchView(new Main()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        getChildren().addAll(
            verticallyCentered(width(512, loginPane)),
            backButton
        );
    }
}
