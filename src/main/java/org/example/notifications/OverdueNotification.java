package org.example.notifications;

public abstract class OverdueNotification implements Notification {
    @Override
    public void showNotification() {
        System.out.println("Показать просроченные задачи: [Список просроченных задач]");
    }
}