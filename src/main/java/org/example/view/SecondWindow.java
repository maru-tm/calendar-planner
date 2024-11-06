package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.Calendar;

public class SecondWindow extends JFrame {

    private JPanel monthPanel;
    private JPanel dayPanel;
    private JPanel taskPanel;
    private int currentYear;

    public SecondWindow() {
        setTitle("Календарь");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Панель для выбора месяца
        monthPanel = new JPanel();
        monthPanel.setLayout(new GridLayout(12, 1));
        add(monthPanel, BorderLayout.WEST);

        // Панель для дней (центр)
        dayPanel = new JPanel();
        dayPanel.setLayout(new GridLayout(0, 7));
        add(dayPanel, BorderLayout.CENTER);

        // Панель для задач (справа)
        taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout());
        add(taskPanel, BorderLayout.EAST);

        initializeMonthPanel();
    }

    private void initializeMonthPanel() {
        String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

        for (int i = 0; i < months.length; i++) {
            JButton monthButton = new JButton(months[i]);
            int monthIndex = i + 1;
            monthButton.addActionListener(new MonthButtonListener(monthIndex));
            monthPanel.add(monthButton);
        }
    }

    private void initializeDayPanel(int month) {
        dayPanel.removeAll();

        YearMonth yearMonth = YearMonth.of(currentYear, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.addActionListener(new DayButtonListener(day));
            dayPanel.add(dayButton);
        }

        dayPanel.revalidate();
        dayPanel.repaint();
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

    private class MonthButtonListener implements ActionListener {
        private final int month;

        public MonthButtonListener(int month) {
            this.month = month;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            initializeDayPanel(month);
        }
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