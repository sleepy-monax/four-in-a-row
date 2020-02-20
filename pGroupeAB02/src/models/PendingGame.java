package models;

public interface PendingGame {
    void kickPlayer(int i);
    void attachListener(PendingGameListener listener);
    void shutdown();
}
