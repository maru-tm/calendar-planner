package org.example.tasks;

import java.time.LocalDate;
public class Task {
    private String description;
    private LocalDate dueDate;
    private boolean completed; // Добавляем состояние выполнения

    public Task(String description, LocalDate dueDate) {
        this.description = description;
        this.dueDate = dueDate != null ? dueDate : LocalDate.now();
        this.completed = false; // по умолчанию задача не выполнена
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

    @Override
    public String toString() {
        return description + " (due: " + dueDate + ")";
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
}
