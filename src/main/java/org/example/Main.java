package org.example;

import org.example.controller.LoginController;
import org.example.view.LoginWindow;
import org.example.model.LoginModel;

public class Main {
    public static void main(String[] args) {
        LoginModel model = new LoginModel();
        LoginWindow view = new LoginWindow();
        LoginController controller = new LoginController(model, view);

        view.setLoginController(controller);
    }
}
