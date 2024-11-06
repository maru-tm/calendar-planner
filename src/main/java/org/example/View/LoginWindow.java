package org.example.View;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    public LoginWindow() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Добро пожаловать!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JLabel loginLabel = new JLabel("Логин:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(loginLabel, gbc);

        JTextField loginField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(loginField, gbc);

        JLabel passwordLabel = new JLabel("Пароль:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        JButton loginButton = new JButton("Войти");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        loginButton.addActionListener(e -> {
            // Открыть главную страницу и закрыть окно логина
            new SecondWindow().setVisible(true);
            dispose(); // Закрыть окно логина
        });
        add(loginButton, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
