package models;

public interface PendingGameListener {
    void onPlayerJoin(int index, String name);

    void onPlayerLeave(int index, String name);

    void onGameTick();
}
