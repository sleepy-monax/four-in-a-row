package views.widgets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class RoomPlayer extends AnchorPane {
    private Label labelName;
    private Label labelStatus;

    public RoomPlayer() {
        labelName = new Label("ERROR");
        labelName.setAlignment(Pos.CENTER_LEFT);
        labelName.getStyleClass().add("username");
        AnchorPane.setLeftAnchor(labelName, 32.0);
        AnchorPane.setTopAnchor(labelName, 0.0);
        AnchorPane.setBottomAnchor(labelName, 0.0);

        labelStatus = new Label("ERROR");
        labelStatus.setAlignment(Pos.CENTER_RIGHT);
        labelStatus.getStyleClass().add("status");
        AnchorPane.setRightAnchor(labelStatus, 32.0);
        AnchorPane.setTopAnchor(labelStatus, 0.0);
        AnchorPane.setBottomAnchor(labelStatus, 0.0);

        this.getChildren().addAll(labelName, labelStatus);

        updateState("", PlayerState.WAITING_FOR_CONNECTION);
    }

    public RoomPlayer updateState(String username, PlayerState state) {
        switch (state) {
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
            case DISCONNECTED:
                labelStatus.setText("Disconnected");
                this.getStyleClass().setAll("FIR_player-state", "FIR_player-state-inactive");
        }

        if (state == PlayerState.WAITING_FOR_CONNECTION) {
            labelName.setText("");
        } else {
            labelName.setText(username);
        }

        return this;
    }
}
