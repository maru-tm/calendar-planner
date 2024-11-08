package org.example.model;

public class LoginModel {
    // Метод для проверки логина и пароля
    public boolean authenticate(String login, String password) {
        return "user".equals(login) && "password".equals(password);
    }
}