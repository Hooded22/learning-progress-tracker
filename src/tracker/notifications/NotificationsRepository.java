package tracker.notifications;

import java.util.ArrayList;
import java.util.List;

public class NotificationsRepository {
    private final List<Notification> notificationsToSend = new ArrayList<>();
    private List<Notification> sendNotifications = new ArrayList<Notification>();

    public NotificationsRepository() {
    }

    public void add(Notification notification) {
        sendNotifications.add(notification);
    }

    public void remove(Notification notification) {
        sendNotifications.remove(notification);
    }

    public List<Notification> getSendNotifications() {
        return sendNotifications;
    }

    public void setSendNotifications(List<Notification> sendNotifications) {
        this.sendNotifications = sendNotifications;
    }

    public List<Notification> getNotificationsToSend() {
        return notificationsToSend;
    }

    public void addNotificationsToSend(Notification notification) {
        notificationsToSend.add(notification);
    }

    public void removeNotificationsToSend(Notification notification) {
        notificationsToSend.remove(notification);
    }

    public void clearNotificationsToSend() {
        notificationsToSend.clear();
    }
}
