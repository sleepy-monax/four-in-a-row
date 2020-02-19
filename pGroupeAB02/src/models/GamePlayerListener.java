package models;

public interface GamePlayerListener {
    void onPlayerJoin(int index, String name);
    void onPlayerLeave(int index, String name);
}
