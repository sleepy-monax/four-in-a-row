package messageloop;

import java.util.HashSet;
import java.util.LinkedList;

public class MessageLoop {
    private LinkedList<Message> pendingRepost = new LinkedList();
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

    private void repost() {
        LinkedList<Message> reposted = new LinkedList<>();

        for (Message message : pendingRepost) {
            if (dispatch(message)) {
                reposted.add(message);
            }
        }

        pendingRepost.removeAll(reposted);
    }

    public void registerNotifier(Notifiable notifier) {
        notifiers.add(notifier);
        repost();
    }

    public void unregisterNotifier(Notifiable notifier) {
        notifiers.remove(notifiers);
    }

    public void post(Message message) {
        if (!dispatch(message) && message.repostable()) {
            pendingRepost.add(message);
        }
    }
}