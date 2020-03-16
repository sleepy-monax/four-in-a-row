package models.messageloop;

public interface Notifiable {
    boolean canAccept(Message message);

    boolean canConsume();

    void handle(Message message);
}