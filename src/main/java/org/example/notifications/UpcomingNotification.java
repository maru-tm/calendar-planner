package org.example.notifications;

public abstract class UpcomingNotification implements Notification {
    @Override
    public void showNotification() {
        System.out.println("Показать предстоящие задачи: [Список предстоящих задач]");
    }
}