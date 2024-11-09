package src.main.java.org.example.tasks;

import java.time.LocalDate;

public class Task {
    private String description;
    private LocalDate dueDate;

    public Task(String description, LocalDate dueDate) {
        this.description = description;
        this.dueDate = dueDate != null ? dueDate : LocalDate.now(); // Устанавливаем дефолтную дату, если не передана
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return description + " (due: " + dueDate + ")";
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
}
