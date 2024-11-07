package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.tasks.*;

public class SecondWindow extends JFrame {

    private JPanel calendarPanel;
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JPanel daysPanel;
    private JPanel taskPanel;

    private int currentYear;
    private int currentMonth;

    // Map to store tasks by date
    private Map<LocalDate, List<Task>> tasksMap = new HashMap<>();

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

        List<Task> tasks = tasksMap.getOrDefault(selectedDate, new ArrayList<>());
        Map<JCheckBox, Task> taskCheckboxMap = new HashMap<>();
        for (Task task : tasks) {
            JCheckBox taskCheckbox = new JCheckBox(task.getDescription());
            taskCheckboxMap.put(taskCheckbox, task);
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
                LocalDate currentDate = LocalDate.now();
                Task newTask = new Task(taskDescription, currentDate);
                JCheckBox newTaskCheckbox = new JCheckBox(newTask.getDescription());
                taskCheckboxMap.put(newTaskCheckbox, newTask);
                taskListPanel.add(newTaskCheckbox);
                taskListPanel.revalidate();
                taskListPanel.repaint();
            }
        });

        saveTaskButton.addActionListener(e -> {
            List<Task> newTasks = new ArrayList<>();
            for (JCheckBox taskCheckbox : taskCheckboxMap.keySet()) {
                if (!taskCheckbox.isSelected()) {
                    newTasks.add(taskCheckboxMap.get(taskCheckbox));
                }
            }
            tasksMap.put(selectedDate, newTasks);
            JOptionPane.showMessageDialog(this, "Задачи сохранены для " + selectedDate);
        });

        clearTasksButton.addActionListener(e -> {
            taskListPanel.removeAll();
            taskCheckboxMap.clear();
            tasksMap.remove(selectedDate);
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
        List<Task> tasks = taskFactory.getTasks(tasksMap, currentDate); // Возвращается список задач

        String taskList = tasks.stream()
                .map(Task::getDescription)
                .reduce("", (acc, desc) -> acc + "\n" + desc);

        JOptionPane.showMessageDialog(this, taskList.isEmpty() ? "Нет задач." : taskList,
                "Список задач", JOptionPane.INFORMATION_MESSAGE);
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
