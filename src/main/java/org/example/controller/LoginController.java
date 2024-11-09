package org.example.controller;

import org.example.view.LoginWindow;
import org.example.view.SecondWindow;
import org.example.model.LoginModel;
public class LoginController {
    private LoginModel model;
    private LoginWindow view;

    // Constructor to initialize the model and view
    public LoginController(LoginModel model, LoginWindow view) {
        this.model = model;
        this.view = view;
    }

    // Method to authenticate the user
    public void authenticate(String login, String password) {
        boolean success = model.authenticate(login, password); // Check the login and password
        if (success) {
            view.showSuccess(); // Show success message
            new SecondWindow().setVisible(true); // Open the second window
        } else {
            view.showError(); // Show error message
        }
    }
}
