package org.example.notifications;

public class OverdueNotificationFactory implements NotificationFactory {
    @Override
    public Notification createNotification() {
        return new OverdueNotification() {
            @Override
            public void showNotification(String message) {

            }
        };
    }
}