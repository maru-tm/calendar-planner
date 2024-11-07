package org.example;

import org.example.Controller.LoginController;
import org.example.View.LoginWindow;
import org.example.model.LoginModel;

public class Main {
    public static void main(String[] args) {
        LoginModel model = new LoginModel();
        LoginWindow view = new LoginWindow();
        LoginController controller = new LoginController(model, view);

        view.setLoginController(controller);
    }
}
