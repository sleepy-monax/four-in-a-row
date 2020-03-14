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
                System.out.println("MESSAGELOOP: dispatching: " + message.getClass().getTypeName() + " to " + notifier);

                notifier.handle(message);
                is_dispatched = true;
            }
        }

        return is_dispatched;
    }

    private void repost() {
        System.out.println("MESSAGELOOP: repost");

        LinkedList<Message> reposted = new LinkedList<>();

        for (Message message : pendings) {
            if (dispatch(message)) {
                reposted.add(message);
            } else {
                System.out.println("MESSAGELOOP: Failled to repost: " + message.getClass().getTypeName());
            }
        }

        pendings.removeAll(reposted);
    }

    public Notifiable registerNotifier(Notifiable notifiable) {
        System.out.println("MESSAGELOOP: registering notifier: " + notifiable);

        notifiables.add(notifiable);
        repost();

        return notifiable;
    }

    public <MessageType> Notifiable registerNotifier(Class<MessageType> type, Consumer<MessageType> consumer) {
        return registerNotifier(new Notifier<MessageType>(type) {
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
        System.out.println("MESSAGELOOP: post: " + message.getClass().getTypeName());

        if (!dispatch(message)) {
            if (message.repostable()) {
                System.out.println("MESSAGELOOP: reposting: " + message.getClass().getTypeName());
                pendings.addLast(message);
            } else {
                System.out.println("MESSAGELOOP: discarding: " + message.getClass().getTypeName());
            }
        }
    }
}