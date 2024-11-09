package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.List;

import org.example.abstractfactory.*;
import org.example.flyweight.PredefinedTaskFactory;
import org.example.singleton.TaskManager;
import org.example.tasks.Task;

public class SecondWindow extends JFrame {

    // Panels and components for the calendar UI
    private JPanel calendarPanel;
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JPanel daysPanel;
    private JPanel taskPanel;

    // Current year and month for calendar initialization
    private int currentYear;
    private int currentMonth;

    // Map for associating checkboxes with tasks
    private Map<JCheckBox, Task> taskCheckboxMap = new HashMap<>();

    // Singleton instance for managing tasks
    private TaskManager taskManager;
    private JPanel taskListPanel;

    public SecondWindow() {
        // Set up the window title, size, and close operation
        setTitle("Календарь");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize current year and month
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        // Create and set up the top panel with combo boxes for selecting the month and year
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

        // Add top panel with month and year combo boxes to the main window
        add(topPanel, BorderLayout.NORTH);

        // Set up the calendar panel with a grid for displaying days of the month
        calendarPanel = new JPanel(new BorderLayout());
        daysPanel = new JPanel(new GridLayout(0, 7));
        calendarPanel.add(daysPanel, BorderLayout.CENTER);

        add(calendarPanel, BorderLayout.CENTER);

        // Set up the task panel for displaying tasks associated with selected day
        taskPanel = new JPanel(new BorderLayout());
        add(taskPanel, BorderLayout.EAST);

        // Update the calendar display
        updateCalendar();
    }

    private void updateCalendar() {
        // Clear the days panel before redrawing the calendar
        daysPanel.removeAll();

        // Display days of the week header
        String[] daysOfWeek = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        for (String day : daysOfWeek) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            daysPanel.add(dayLabel);
        }

        // Get selected year and month, and calculate the number of days in the month
        int selectedYear = (int) yearComboBox.getSelectedItem();
        int selectedMonth = monthComboBox.getSelectedIndex() + 1;

        YearMonth yearMonth = YearMonth.of(selectedYear, selectedMonth);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        // Add empty cells before the first day to align the calendar correctly
        for (int i = 1; i < startDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }

        // Add buttons for each day of the month
        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.addActionListener(new DayButtonListener(day));
            daysPanel.add(dayButton);
        }

        // Revalidate and repaint the calendar panel
        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private void initializeTaskPanel(int day) {
        // Clear the task panel and reset the checkbox map
        taskPanel.removeAll();
        taskCheckboxMap.clear();

        // Get selected date for the task panel
        LocalDate selectedDate = LocalDate.of((int) yearComboBox.getSelectedItem(), monthComboBox.getSelectedIndex() + 1, day);

        // Label to indicate the date for which tasks are shown
        JLabel dayLabel = new JLabel("Задачи на " + day + "-е число:");
        JPanel taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));

        // Task buttons
        JButton addTaskButton = new JButton("Добавить задачу");
        JButton addFromListButton = new JButton("Добавить из списка");
        JButton saveTaskButton = new JButton("Сохранить задачи");
        JButton clearTasksButton = new JButton("Удалить выбранные задачи");
        JButton editTaskButton = new JButton("Изменить выбранную задачу");
        JButton filterButtonOverdue = new JButton("Показать просроченные задачи");
        JButton filterButtonUpcoming = new JButton("Показать предстоящие задачи");


        // Get task manager instance and load tasks for the selected date (Singleton)
        TaskManager taskManager = TaskManager.getInstance();

        List<Task> tasks = taskManager.getTasks(selectedDate);
        for (Task task : tasks) {
            JCheckBox taskCheckbox = new JCheckBox(task.getDescription(), task.isCompleted());
            taskCheckboxMap.put(taskCheckbox, task);
            taskListPanel.add(taskCheckbox);
        }

        // Add task list and buttons to the task panel
        taskPanel.add(dayLabel, BorderLayout.NORTH);
        taskPanel.add(new JScrollPane(taskListPanel), BorderLayout.CENTER);

        // Panel for task action buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(addTaskButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(addFromListButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(saveTaskButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(clearTasksButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(editTaskButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(filterButtonOverdue);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(filterButtonUpcoming);
        buttonPanel.add(Box.createVerticalStrut(5));

        taskPanel.add(buttonPanel, BorderLayout.SOUTH);

        taskPanel.revalidate();
        taskPanel.repaint();

        // Add action listener for adding a new task
        addTaskButton.addActionListener(e -> {
            String taskDescription = JOptionPane.showInputDialog("Введите задачу:");
            if (taskDescription != null && !taskDescription.isEmpty()) {
                Task newTask = new Task(taskDescription, selectedDate);
                taskManager.addTask(newTask);

                JCheckBox newTaskCheckbox = new JCheckBox(newTask.getDescription());
                taskCheckboxMap.put(newTaskCheckbox, newTask);
                taskListPanel.add(newTaskCheckbox);

                taskListPanel.revalidate();
                taskListPanel.repaint();
            }
        });

        // Add action listener for adding a task from predefined list (Flyweight)
        addFromListButton.addActionListener(e -> {
            Map<String, Task> predefinedTasks = PredefinedTaskFactory.getAllPredefinedTasks();
            String[] taskDescriptions = predefinedTasks.keySet().toArray(new String[0]);

            String selectedTaskDescription = (String) JOptionPane.showInputDialog(
                    this,
                    "Выберите задачу:",
                    "Добавить из списка",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    taskDescriptions,
                    taskDescriptions[0]
            );

            if (selectedTaskDescription != null) {
                Task selectedTask = PredefinedTaskFactory.getTask(selectedTaskDescription);

                if (selectedTask != null) {
                    // Копируем задачу и добавляем её к выбранной дате
                    Task newTask = new Task(selectedTask.getDescription(), selectedDate);
                    taskManager.addTask(newTask);

                    JCheckBox newTaskCheckbox = new JCheckBox(newTask.getDescription());
                    taskCheckboxMap.put(newTaskCheckbox, newTask);
                    taskListPanel.add(newTaskCheckbox);

                    taskListPanel.revalidate();
                    taskListPanel.repaint();
                }
            }
        });

        // Save task status (completed or not) to task manager
        saveTaskButton.addActionListener(e -> {
            for (Map.Entry<JCheckBox, Task> entry : taskCheckboxMap.entrySet()) {
                JCheckBox checkbox = entry.getKey();
                Task task = entry.getValue();
                task.setCompleted(checkbox.isSelected());
            }
            JOptionPane.showMessageDialog(this, "Задачи сохранены.");
        });

        // Clear selected tasks (delete checked tasks)
        clearTasksButton.addActionListener(e -> {
            taskCheckboxMap.entrySet().removeIf(entry -> {
                if (entry.getKey().isSelected()) {
                    taskManager.getTasks(selectedDate).remove(entry.getValue());
                    taskListPanel.remove(entry.getKey());
                    return true;
                }
                return false;
            });
            taskListPanel.revalidate();
            taskListPanel.repaint();
            JOptionPane.showMessageDialog(this, "Выбранные задачи удалены.");
        });

        // Edit selected task
        editTaskButton.addActionListener(e -> {
            for (Map.Entry<JCheckBox, Task> entry : taskCheckboxMap.entrySet()) {
                if (entry.getKey().isSelected()) {
                    String newDescription = JOptionPane.showInputDialog("Измените задачу:", entry.getValue().getDescription());
                    if (newDescription != null && !newDescription.isEmpty()) {
                        entry.getValue().setDescription(newDescription);
                        entry.getKey().setText(newDescription);
                    }
                }
            }
            taskListPanel.revalidate();
            taskListPanel.repaint();
        });

        // For the "Show Overdue Tasks" button
        filterButtonOverdue.addActionListener(e -> {
            // Apply the filter for overdue tasks
            TaskFilter overdueFilter = new OverdueTaskFilter();
            List<Task> filteredTasks = taskManager.getTasksByFilter(overdueFilter);

            // Create and display the window for showing the filtered tasks
            FilterWindow filterWindow = new FilterWindow(filteredTasks); // передаем отфильтрованные задачи
            filterWindow.setVisible(true);
        });

        // For the "Show Upcoming Tasks" button
        filterButtonUpcoming.addActionListener(e -> {
            // Apply the filter for upcoming tasks
            TaskFilter upcomingFilter = new UpcomingTaskFilter();
            List<Task> filteredTasks = taskManager.getTasksByFilter(upcomingFilter);

            // Create and display the window for showing the filtered tasks
            FilterWindow filterWindow = new FilterWindow(filteredTasks); // передаем отфильтрованные задачи
            filterWindow.setVisible(true);
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