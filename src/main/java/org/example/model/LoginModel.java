package org.example.model;

public class LoginModel {
    // This method checks if the provided login and password match predefined values
    public boolean authenticate(String login, String password) {
        return "user".equals(login) && "password".equals(password);
    }
}
