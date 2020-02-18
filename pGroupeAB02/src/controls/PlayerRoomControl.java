package controls;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


public class PlayerRoomControl extends AnchorPane {
    private PlayerRoomControlState state;
    private String username;

    private Label labelName;
    private Label labelStatus;

    public PlayerRoomControl() {
        labelName = new Label("ERROR");
        labelName.setAlignment(Pos.CENTER_LEFT);
        labelName.getStyleClass().add("username");
        AnchorPane.setLeftAnchor(labelName, 32.0);

        labelStatus = new Label("ERROR");
        labelStatus.setAlignment(Pos.CENTER_RIGHT);
        labelStatus.getStyleClass().add("status");
        AnchorPane.setRightAnchor(labelStatus, 32.0);

        this.getChildren().addAll(labelName, labelStatus);
    }

    public PlayerRoomControl updateState(String username, PlayerRoomControlState state) {
        switch (state){
            case CONNECTED:
                labelStatus.setText("Connected");
                this.getStyleClass().setAll("FIR_player-state", "FIR_player-state-connected");
                break;
            case CONNECTING:
                labelStatus.setText("Connecting...");
                this.getStyleClass().setAll("FIR_player-state", "FIR_player-state-inactive");
                break;
            case WAITING_FOR_CONNECTION:
                labelStatus.setText("Waiting for player...");
                this.getStyleClass().setAll("FIR_player-state", "FIR_player-state-inactive");
                break;
        }

        if (state == PlayerRoomControlState.WAITING_FOR_CONNECTION) {
            this.username = "";
            labelName.setText("");
        } else {
            this.username = username;
            labelName.setText(username);
        }

        this.state = state;

        return this;
    }

    public PlayerRoomControlState getState() {
        return state;
    }

    public String getUsername() {
        return username;
    }
}
