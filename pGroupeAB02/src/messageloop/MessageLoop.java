package messageloop;

import java.util.HashSet;
import java.util.LinkedList;

public class MessageLoop {
    private LinkedList<Message> pending = new LinkedList();
    private HashSet<Notifiable> notifiers = new HashSet();

    public MessageLoop() {
    }

    private boolean dispatch(Message message) {
        boolean is_dispatched = false;

        for (Notifiable notifier : notifiers) {
            if (notifier.canAccept(message)) {
                notifier.handle(message);
                is_dispatched = true;
            }
        }

        return is_dispatched;
    }

    private void redispatch() {
        LinkedList<Message> redispatched = new LinkedList<>();

        for (Message message : pending) {
            if (dispatch(message)) {
                redispatched.add(message);
            }
        }

        pending.removeAll(redispatched);
    }

    void registerNotifier(Notifiable notifier) {
        notifiers.add(notifier);
        redispatch();
    }

    void unregisterNotifier(Notifiable notifier) {
        notifiers.remove(notifiers);
    }

    void post(Message message) {
        if (!dispatch(message)) {
            pending.add(message);
        }
    }
}