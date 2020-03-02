package messageloop;

public interface Notifiable {
    boolean canAccept(Message message);

    void handle(Message message);
}