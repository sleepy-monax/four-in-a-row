package models;

import controls.PlayerRoomControlState;

public interface PendingGame {
    void kickPlayer(int i);

    void attachListener(PendingGameListener listener);

    void shutdown();

    String getPlayerName(int id);

    PlayerRoomControlState getPlayerState(int id);
}
