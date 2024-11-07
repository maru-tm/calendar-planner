package org.example.notifications;

public class UpcomingNotificationFactory implements NotificationFactory {
    @Override
    public Notification createNotification() {
        return new UpcomingNotification() {
            @Override
            public void showNotification(String message) {

            }
        };
    }
}