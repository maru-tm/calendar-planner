package org.example.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;

public class SecondWindow extends JFrame {

    private JPanel calendarPanel;
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JPanel daysPanel;
    private JPanel taskPanel;

    private int currentYear;
    private int currentMonth;

    public SecondWindow() {
        setTitle("Календарь");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        // Панель для выбора месяца и года
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        // Месяцы
        String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setSelectedIndex(currentMonth - 1);
        monthComboBox.addActionListener(e -> updateCalendar());
        topPanel.add(monthComboBox);

        // Год
        yearComboBox = new JComboBox<>();
        for (int i = currentYear - 5; i <= currentYear + 5; i++) {
            yearComboBox.addItem(i);
        }
        yearComboBox.setSelectedItem(currentYear);
        yearComboBox.addActionListener(e -> updateCalendar());
        topPanel.add(yearComboBox);

        add(topPanel, BorderLayout.NORTH);

        // Панель для дней недели и дней месяца
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new BorderLayout());

        daysPanel = new JPanel();
        daysPanel.setLayout(new GridLayout(0, 7));
        calendarPanel.add(daysPanel, BorderLayout.CENTER);

        add(calendarPanel, BorderLayout.CENTER);

        // Панель для задач
        taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout());
        add(taskPanel, BorderLayout.EAST);

        updateCalendar(); // Обновление календаря при загрузке окна
    }

    private void updateCalendar() {
        daysPanel.removeAll();

        // Отображение дней недели
        String[] daysOfWeek = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        for (String day : daysOfWeek) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            daysPanel.add(dayLabel);
        }

        // Определение выбранного месяца и года
        int selectedYear = (int) yearComboBox.getSelectedItem();
        int selectedMonth = monthComboBox.getSelectedIndex() + 1;

        YearMonth yearMonth = YearMonth.of(selectedYear, selectedMonth);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        // Добавление пустых ячеек для смещения дней начала месяца
        for (int i = 1; i < startDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }

        // Отображение дней месяца
        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.addActionListener(new DayButtonListener(day));
            daysPanel.add(dayButton);
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private void initializeTaskPanel(int day) {
        taskPanel.removeAll();

        JLabel dayLabel = new JLabel("Задачи на " + day + "-е число:");
        JTextArea taskArea = new JTextArea(10, 20);
        JButton addTaskButton = new JButton("Добавить задачу");

        taskPanel.add(dayLabel, BorderLayout.NORTH);
        taskPanel.add(new JScrollPane(taskArea), BorderLayout.CENTER);
        taskPanel.add(addTaskButton, BorderLayout.SOUTH);

        taskPanel.revalidate();
        taskPanel.repaint();

        addTaskButton.addActionListener(e -> {
            String task = JOptionPane.showInputDialog("Введите задачу:");
            if (task != null && !task.isEmpty()) {
                taskArea.append(task + "\n");
            }
        });
    }

    private class DayButtonListener implements ActionListener {
        private final int day;

        public DayButtonListener(int day) {
            this.day = day;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            initializeTaskPanel(day);
        }
    }
}
