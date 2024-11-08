package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

import org.example.singleton.TaskManager;
import org.example.tasks.*;

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

        JPanel topPanel = new JPanel(new FlowLayout());

        String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setSelectedIndex(currentMonth - 1);
        monthComboBox.addActionListener(e -> updateCalendar());
        topPanel.add(monthComboBox);

        yearComboBox = new JComboBox<>();
        for (int i = currentYear - 5; i <= currentYear + 5; i++) {
            yearComboBox.addItem(i);
        }
        yearComboBox.setSelectedItem(currentYear);
        yearComboBox.addActionListener(e -> updateCalendar());
        topPanel.add(yearComboBox);

        add(topPanel, BorderLayout.NORTH);

        calendarPanel = new JPanel(new BorderLayout());
        daysPanel = new JPanel(new GridLayout(0, 7));
        calendarPanel.add(daysPanel, BorderLayout.CENTER);

        add(calendarPanel, BorderLayout.CENTER);

        taskPanel = new JPanel(new BorderLayout());
        add(taskPanel, BorderLayout.EAST);

        updateCalendar();
    }

    private void updateCalendar() {
        daysPanel.removeAll();

        String[] daysOfWeek = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        for (String day : daysOfWeek) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            daysPanel.add(dayLabel);
        }

        int selectedYear = (int) yearComboBox.getSelectedItem();
        int selectedMonth = monthComboBox.getSelectedIndex() + 1;

        YearMonth yearMonth = YearMonth.of(selectedYear, selectedMonth);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i < startDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }

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

        LocalDate selectedDate = LocalDate.of((int) yearComboBox.getSelectedItem(), monthComboBox.getSelectedIndex() + 1, day);

        JLabel dayLabel = new JLabel("Задачи на " + day + "-е число:");
        JPanel taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));

        JButton addTaskButton = new JButton("Добавить задачу");
        JButton saveTaskButton = new JButton("Сохранить задачи");
        JButton clearTasksButton = new JButton("Удалить все задачи");
        JButton showUpcomingTasksButton = new JButton("Показать предстоящие задачи");
        JButton showOverdueTasksButton = new JButton("Показать просроченные задачи");

        TaskManager taskManager = TaskManager.getInstance();

        List<Task> tasks = taskManager.getTasks(selectedDate);
        for (Task task : tasks) {
            JCheckBox taskCheckbox = new JCheckBox(task.getDescription());
            taskListPanel.add(taskCheckbox);
        }

        taskPanel.add(dayLabel, BorderLayout.NORTH);
        taskPanel.add(new JScrollPane(taskListPanel), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(addTaskButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(saveTaskButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(clearTasksButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(showUpcomingTasksButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(showOverdueTasksButton);

        taskPanel.add(buttonPanel, BorderLayout.SOUTH);

        taskPanel.revalidate();
        taskPanel.repaint();

        addTaskButton.addActionListener(e -> {
            String taskDescription = JOptionPane.showInputDialog("Введите задачу:");
            if (taskDescription != null && !taskDescription.isEmpty()) {
                Task newTask = new Task(taskDescription, selectedDate);
                taskManager.addTask(newTask);

                JCheckBox newTaskCheckbox = new JCheckBox(newTask.getDescription());
                taskListPanel.add(newTaskCheckbox);

                taskListPanel.revalidate();
                taskListPanel.repaint();
            }
        });

        clearTasksButton.addActionListener(e -> {
            taskManager.removeTasks(selectedDate);
            taskListPanel.removeAll();
            taskListPanel.revalidate();
            taskListPanel.repaint();
            JOptionPane.showMessageDialog(this, "Все задачи удалены для " + selectedDate);
        });

        showUpcomingTasksButton.addActionListener(e -> showTasks(new UpcomingTaskAbstractFactory()));
        showOverdueTasksButton.addActionListener(e -> showTasks(new OverdueTaskAbstractFactory()));
    }

    private void showTasks(AbstractTaskFactory factory) {
        LocalDate currentDate = LocalDate.now();
        TaskFactory taskFactory = factory.createTaskFactory();

        TaskManager taskManager = TaskManager.getInstance();
        Map<LocalDate, List<Task>> allTasks = taskManager.getAllTasksMap();
        List<Task> tasks = taskFactory.getTasks(allTasks, currentDate);

        JPanel taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        for (Task task : tasks) {
            JCheckBox taskCheckbox = new JCheckBox(task.getDescription());
            taskListPanel.add(taskCheckbox);
        }

        taskPanel.add(new JScrollPane(taskListPanel), BorderLayout.CENTER);
        taskPanel.revalidate();
        taskPanel.repaint();
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
