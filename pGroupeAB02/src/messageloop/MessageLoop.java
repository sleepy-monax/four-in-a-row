package messageloop;

import java.util.HashSet;
import java.util.LinkedList;

public class MessageLoop {
    private LinkedList<Message> pendings = new LinkedList<>();
    private HashSet<Notifiable> notifiables = new HashSet<>();

    public MessageLoop() {
    }

    private boolean dispatch(Message message) {
        boolean is_dispatched = false;

        for (Notifiable notifier : notifiables) {
            if (notifier.canAccept(message)) {
                notifier.handle(message);
                is_dispatched = true;
            }
        }

        return is_dispatched;
    }

    private void repost() {
        LinkedList<Message> reposted = new LinkedList<>();

        for (Message message : pendings) {
            if (dispatch(message)) {
                reposted.add(message);
            }
        }

        pendings.removeAll(reposted);
    }

    public void registerNotifier(Notifiable notifiable) {
        notifiables.add(notifiable);
        repost();
    }

    public void unregisterNotifier(Notifiable notifiable) {
        notifiables.remove(notifiable);
    }

    public void post(Message message) {
        if (!dispatch(message) && message.repostable()) {
            pendings.add(message);
        }
    }
}