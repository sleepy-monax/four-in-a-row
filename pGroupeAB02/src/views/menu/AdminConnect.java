package views.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import models.Deck;
import models.Users.User;
import utils.StageManager;
import views.Layout;
import views.TextStyle;
import views.View;
import views.Widget;
import views.dialogs.InfoDialog;

import static views.Layout.*;
import static views.Widget.*;

public class AdminConnect extends View {
    User admin = new User("admin","helha");


    public AdminConnect() {
        setPadding(new Insets(32));
        TextField logField = textField();
        PasswordField pswField = passwordField();

        Node menu =
                vertical(
                        16,
                        text("Username", TextStyle.SUBTITLE),
                        logField,
                        text("Password", TextStyle.SUBTITLE),
                        pswField,
                        Layout.horizontallyCentered(
                        button("Connect", event -> {
                            User usr = new User(logField.getText(), pswField.getText());
                            if (usr.equals(admin)) {
                                StageManager.switchView(new Editor(Deck.load()));
                            }else {
                                new InfoDialog("Error","Wrong login or password").show();
                            }
                        }))
                );


        Button backButton = Widget.button("Go back", actionEvent -> StageManager.switchView(new Main()));
        StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

        getChildren().addAll(Widget.text("Connect", TextStyle.TITLE), Layout.verticallyCentered(Layout.width(512, Widget.panel(menu))), backButton);
    }
}
