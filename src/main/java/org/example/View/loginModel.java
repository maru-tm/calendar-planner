package org.example.View;

import java.util.ArrayList;
import java.util.List;

public class loginModel {
    private List<LoginObserver> observers = new ArrayList<>();

    // Метод для добавления наблюдателя
    public void addObserver(LoginObserver observer) {
        observers.add(observer);
    }

    // Метод для уведомления наблюдателей о статусе входа
    private void notifyObservers(boolean success) {
        for (LoginObserver observer : observers) {
            observer.onLoginResult(success);
        }
    }

    // Проверка логина и пароля
    public void authenticate(String login, String password) {
        boolean success = "user".equals(login) && "password".equals(password);
        notifyObservers(success);
    }

    // Интерфейс для наблюдателей
    public interface LoginObserver {
        void onLoginResult(boolean success);
    }
}
