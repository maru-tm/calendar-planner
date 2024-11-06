package org.example.notifications;

public class TaskNotificationFactory implements NotificationAbstractFactory{
    @Override
    public Notification createUpcomingNotification() {
        return new UpcomingNotification();
    }
    @Override
    public Notification createOverdueNotification() {
        return new OverdueNotification();
    }

}
