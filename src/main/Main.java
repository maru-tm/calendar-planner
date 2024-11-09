package main;

import view.CalendarView;

public class Main {
    public static void main(String[] args) {
        // Создаем и запускаем приложение в потоке Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            CalendarView calendarView = new CalendarView();
            calendarView.setVisible(true); // Убедитесь, что окно будет отображаться
        });
    }
}
