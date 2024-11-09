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
import org.example.singleton.TaskManager;
import org.example.tasks.Task;

public class SecondWindow extends JFrame {

    private JPanel calendarPanel;
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JPanel daysPanel;
    private JPanel taskPanel;

    private int currentYear;
    private int currentMonth;

    // Карта для связывания чекбоксов с задачами
    private Map<JCheckBox, Task> taskCheckboxMap = new HashMap<>();

    private TaskManager taskManager;
    private JPanel taskListPanel;

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
        taskCheckboxMap.clear(); // Очистка карты перед загрузкой задач

        LocalDate selectedDate = LocalDate.of((int) yearComboBox.getSelectedItem(), monthComboBox.getSelectedIndex() + 1, day);

        JLabel dayLabel = new JLabel("Задачи на " + day + "-е число:");
        JPanel taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));

        JButton addTaskButton = new JButton("Добавить задачу");
        JButton saveTaskButton = new JButton("Сохранить задачи");
        JButton clearTasksButton = new JButton("Удалить выбранные задачи");
        JButton editTaskButton = new JButton("Изменить выбранную задачу");
        JButton filterButton = new JButton("Фильтр");

        TaskManager taskManager = TaskManager.getInstance();

        List<Task> tasks = taskManager.getTasks(selectedDate);
        for (Task task : tasks) {
            JCheckBox taskCheckbox = new JCheckBox(task.getDescription(), task.isCompleted());
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
        buttonPanel.add(editTaskButton);
        buttonPanel.add(filterButton);


        taskPanel.add(buttonPanel, BorderLayout.SOUTH);

        taskPanel.revalidate();
        taskPanel.repaint();

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

        saveTaskButton.addActionListener(e -> {
            for (Map.Entry<JCheckBox, Task> entry : taskCheckboxMap.entrySet()) {
                JCheckBox checkbox = entry.getKey();
                Task task = entry.getValue();
                task.setCompleted(checkbox.isSelected());
            }
            JOptionPane.showMessageDialog(this, "Задачи сохранены.");
        });

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

        filterButton.addActionListener(e -> {
            // Выбор фильтра
            TaskFilter filter = new OverdueTaskFilter(); // Или другой фильтр
            List<Task> filteredTasks = taskManager.getTasksByFilter(filter);

            // Создаем и отображаем окно для отображения отфильтрованных задач
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
