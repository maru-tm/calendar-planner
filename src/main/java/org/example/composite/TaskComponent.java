package org.example.composite;

public interface TaskComponent {
    // Method to display the task
    void showTask();

    // Method to add a sub-task to the task (only supported by composite tasks)
    void addTask(TaskComponent task);

    // Method to remove a sub-task from the task (only supported by composite tasks)
    void removeTask(TaskComponent task);
}
