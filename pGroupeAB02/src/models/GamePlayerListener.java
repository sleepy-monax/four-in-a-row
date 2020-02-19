package models;

public abstract interface GamePlayerListener {
    public abstract void onPlayerJoin(int index, String name);
    public abstract void onPlayerLeave(int index, String name);
}
