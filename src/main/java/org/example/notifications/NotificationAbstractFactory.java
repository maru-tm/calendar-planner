package org.example.notifications;

public interface NotificationAbstractFactory {
    Notification createUpcomingNotification();
    Notification createOverdueNotification();
}
