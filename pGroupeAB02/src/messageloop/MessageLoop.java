package messageloop;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.Consumer;

public class MessageLoop {
    private LinkedList<Message> pendings = new LinkedList<>();
    private HashSet<Notifiable> notifiables = new HashSet<>();

    public MessageLoop() {
    }

    private boolean dispatch(Message message) {
        boolean is_dispatched = false;

        for (Notifiable notifier : notifiables) {
            if (notifier.canAccept(message)) {
                System.out.println("MESSAGELOOP dispatch: " + message.getClass().getTypeName() + " to " + notifier);

                notifier.handle(message);
                is_dispatched = true;
            }
        }

        return is_dispatched;
    }

    private void repost() {
        System.out.println("MESSAGELOOP repost");

        LinkedList<Message> reposted = new LinkedList<>();

        for (Message message : pendings) {
            if (dispatch(message)) {
                reposted.add(message);
            } else {
                System.out.println("MESSAGELOOP Failled to repost: " + message.getClass().getTypeName());
            }
        }

        pendings.removeAll(reposted);
    }

    public void registerNotifier(Notifiable notifiable) {
        notifiables.add(notifiable);
        repost();
    }

    public <MessageType> void registerNotifier(Class<MessageType> type, Consumer<MessageType> consumer) {
        registerNotifier(new Notifier<MessageType>(type) {
            @Override
            public void handle(MessageType message) {
                consumer.accept(message);
            }
        });
    }

    public void unregisterNotifier(Notifiable notifiable) {
        notifiables.remove(notifiable);
    }

    public void post(Message message) {
        System.out.println("MESSAGELOOP post: " + message.getClass().getTypeName());

        if (!dispatch(message) && message.repostable()) {
            System.out.println("MESSAGELOOP failled to post: " + message.getClass().getTypeName());
            pendings.add(message);
        }
    }
}