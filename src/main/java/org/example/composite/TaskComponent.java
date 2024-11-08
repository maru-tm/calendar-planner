package org.example.composite;

public interface TaskComponent {
    void addTask(TaskComponent task);
    void removeTask(TaskComponent task);
    void markCompleted();
    boolean isCompleted();
    String getDescription();
}
