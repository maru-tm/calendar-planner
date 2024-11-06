package org.example.notifications;

public class EventNotificationFactory implements NotificationAbstractFactory{
    @Override
    public Notification createUpcomingNotification() {
        return new UpcomingNotification(); // Можно также создать Event-specific Notification
    }

    @Override
    public Notification createOverdueNotification() {
        return new OverdueNotification(); // Можно также создать Event-specific Notification
    }
}
