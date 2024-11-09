package src.main.java.org.example.controller;

import src.main.java.org.example.view.LoginWindow;
import src.main.java.org.example.view.SecondWindow;
import src.main.java.org.example.model.LoginModel;

public class LoginController {
    private LoginModel model;
    private LoginWindow view;

    public LoginController(LoginModel model, LoginWindow view) {
        this.model = model;
        this.view = view;
    }

    public void authenticate(String login, String password) {
        boolean success = model.authenticate(login, password);
        if (success) {
            view.showSuccess();
            new SecondWindow().setVisible(true);

        } else {
            view.showError();
        }
    }
}