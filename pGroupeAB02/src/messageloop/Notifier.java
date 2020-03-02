package messageloop;

public abstract class Notifier<MessageType> implements Notifiable {
    private Class<MessageType> messageTypeClass;

    @Override
    public final boolean canAccept(Message message) {
        return message.getClass() == messageTypeClass;
    }

    @Override
    public final void handle(Message message) {
        handle((MessageType) message);
    }

    public abstract void handle(MessageType message);
}