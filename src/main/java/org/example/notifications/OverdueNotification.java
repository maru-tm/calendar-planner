package org.example.notifications;

public class OverdueNotification implements Notification{
    @Override
    public void notifyUser(){
        System.out.println("Уведомление: просроченная задача!");
    }
}
