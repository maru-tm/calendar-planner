package org.example.view;

import org.example.singleton.*;
import org.example.strategy.DateSortStrategy;
import org.example.strategy.DescriptionSortStrategy;
import org.example.tasks.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FilterWindow extends JFrame {

    private JPanel taskPanel;
    private TaskManager taskManager;

    // Constructor
    public FilterWindow(List<Task> tasks) {
        this.taskManager = new TaskManager();
        setTitle("Фильтр задач");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Button for sorting tasks by date
        JButton dateSortButton = new JButton("Сортировать по дате");
        dateSortButton.addActionListener(e -> {
            taskManager.setSortStrategy(new DateSortStrategy()); // Set the sorting strategy to DateSortStrategy
            List<Task> sortedTasks = taskManager.getSortedTasks(tasks); // Get sorted tasks
            updateTaskPanel(sortedTasks);
        });
        buttonPanel.add(dateSortButton);

        // Button for sorting tasks by description
        JButton descriptionSortButton = new JButton("Сортировать по задаче");
        descriptionSortButton.addActionListener(e -> {
            taskManager.setSortStrategy(new DescriptionSortStrategy());
            List<Task> sortedTasks = taskManager.getSortedTasks(tasks);
            updateTaskPanel(sortedTasks); // Обновляем панель
        });
        buttonPanel.add(descriptionSortButton);

        // Panel to display tasks
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));

        // Initial display of tasks
        updateTaskPanel(tasks);

        // Add the panels to the window
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskPanel), BorderLayout.CENTER);
    }

    // Method to update the task panel with new tasks
    private void updateTaskPanel(List<Task> tasks) {
        taskPanel.removeAll();
        for (Task task : tasks) {
            // Create a string for each task displaying due date and description
            String taskDescription = task.getDueDate().toString() + "\n" + task.getDescription();
            JCheckBox taskCheckBox = new JCheckBox(taskDescription);
            taskPanel.add(taskCheckBox);
        }
        taskPanel.revalidate();
        taskPanel.repaint();
    }
}
