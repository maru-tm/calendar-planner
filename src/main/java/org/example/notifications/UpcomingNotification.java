package org.example.notifications;

public class UpcomingNotification implements Notification{
    @Override
    public void notifyUser(){
        System.out.println("Уведомление: предстоящая задача!");
    }
}
