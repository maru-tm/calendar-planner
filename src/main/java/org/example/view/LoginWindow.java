package org.example.view;

import org.example.controller.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private LoginController loginController;

    // Constructor to set up the login window
    public LoginWindow() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Label for the login field
        JLabel loginLabel = new JLabel("Логин:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(loginLabel, gbc);

        // Text field for entering login
        loginField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(loginField, gbc);

        // Label for the password field
        JLabel passwordLabel = new JLabel("Пароль:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        // Password field for entering password
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        // Button for login action
        JButton loginButton = new JButton("Войти");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());
            if (loginController != null) {
                loginController.authenticate(login, password);
            }
        });
        add(loginButton, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to set the LoginController
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    // Method to display success message and open the SecondWindow
    public void showSuccess() {
        JOptionPane.showMessageDialog(this, "Успешный вход!");
        new SecondWindow();
        dispose(); // Закрываем окно входа
    }

    // Method to display error message for invalid login or password
    public void showError() {
        JOptionPane.showMessageDialog(this, "Неверный логин или пароль. Попробуйте снова.");
    }
}