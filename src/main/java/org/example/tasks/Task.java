package org.example.tasks;

import java.time.LocalDate;

public class Task {
    private String description;
    private LocalDate dueDate;
    private boolean completed;

    public Task(String description, LocalDate dueDate) {
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Метод для проверки, просрочена ли задача
    public boolean isOverdue() {
        // Проверяем, что дата задачи раньше текущей и задача не завершена
        return dueDate.isBefore(LocalDate.now()) && !completed;
    }

    public boolean isUpcoming() {
        // Задача считается предстоящей, если она не просрочена и не завершена
        return !isOverdue(); // или dueDate.isAfter(LocalDate.now()) || dueDate.isEqual(LocalDate.now())
    }
    @Override
    public String toString() {
        return description + " (Due: " + dueDate + ")";
    }
}
