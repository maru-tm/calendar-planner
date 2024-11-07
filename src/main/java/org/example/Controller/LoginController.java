package org.example.Controller;

import org.example.View.LoginWindow;
import org.example.View.SecondWindow;
import org.example.model.LoginModel;

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
