package org.example.composite;

public class SimpleTask implements TaskComponent {
    private String taskName;

    // Constructor to initialize the task name
    public SimpleTask(String taskName) {
        this.taskName = taskName;
    }

    // Method to display the task name
    @Override
    public void showTask() {
        System.out.println("Task: " + taskName);
    }

    // Cannot add sub-tasks to a simple task, throws exception
    @Override
    public void addTask(TaskComponent task) {
        throw new UnsupportedOperationException("Cannot add task to a simple task.");
    }

    // Cannot remove sub-tasks from a simple task, throws exception
    @Override
    public void removeTask(TaskComponent task) {
        throw new UnsupportedOperationException("Cannot remove task from a simple task.");
    }
}
