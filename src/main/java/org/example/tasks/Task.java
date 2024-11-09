package org.example.tasks;

import java.time.LocalDate;

public class Task {
    private String description;
    private LocalDate dueDate;
    private boolean completed;

    // Constructor to initialize a task with description and due date
    public Task(String description, LocalDate dueDate) {
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
    }

    // Getter for task description
    public String getDescription() {
        return description;
    }

    // Getter for due date
    public LocalDate getDueDate() {
        return dueDate;
    }

    // Getter for task completion status
    public boolean isCompleted() {
        return completed;
    }

    // Setter for task completion status
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Setter for task description
    public void setDescription(String description) {
        this.description = description;
    }

    // Checks if the task is overdue (due date is before today and not completed)
    public boolean isOverdue() {
        return dueDate.isBefore(LocalDate.now()) && !completed;
    }

    // Checks if the task is upcoming (not overdue)
    public boolean isUpcoming() {
        return !isOverdue();
    }

    // Converts the task to a string representation
    @Override
    public String toString() {
        return description + " (Due: " + dueDate + ")";
    }
}
