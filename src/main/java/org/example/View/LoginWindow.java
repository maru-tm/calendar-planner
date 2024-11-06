package org.example.View;

import org.example.View.loginModel;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame implements loginModel.LoginObserver {
    private JTextField loginField;
    private JPasswordField passwordField;
    private loginModel loginModel;

    public LoginWindow() {
        loginModel = new loginModel();
        loginModel.addObserver(this); // Подписываем окно на события из LoginModel

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

        loginField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(loginField, gbc);

        JLabel passwordLabel = new JLabel("Пароль:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        JButton loginButton = new JButton("Войти");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        // Обработка события нажатия кнопки "Войти"
        loginButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());
            loginModel.authenticate(login, password); // Проверяем логин и пароль через модель
        });
        add(loginButton, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Реализация метода интерфейса LoginObserver
    @Override
    public void onLoginResult(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, "Успешный вход!");
            new SecondWindow().setVisible(true); // Открываем SecondWindow
            dispose(); // Закрываем окно логина
        } else {
            JOptionPane.showMessageDialog(this, "Неверный логин или пароль. Попробуйте снова.");
        }
    }
}
