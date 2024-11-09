package src.main.java.org.example;

import src.main.java.org.example.controller.LoginController;
import src.main.java.org.example.view.LoginWindow;
import src.main.java.org.example.model.LoginModel;

public class Main {
    public static void main(String[] args) {
        LoginModel model = new LoginModel();
        LoginWindow view = new LoginWindow();
        LoginController controller = new LoginController(model, view);

        view.setLoginController(controller);
    }
}