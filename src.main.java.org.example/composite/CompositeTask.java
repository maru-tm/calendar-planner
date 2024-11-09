package src.main.java.org.example.composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeTask implements TaskComponent {
    private final String description;
    private final List<TaskComponent> subtasks = new ArrayList<>();

    public CompositeTask(String description) {
        this.description = description;
    }

    @Override
    public void addTask(TaskComponent task) {
        subtasks.add(task);
    }

    @Override
    public void removeTask(TaskComponent task) {
        subtasks.remove(task);
    }

    @Override
    public void markCompleted() {
        subtasks.forEach(TaskComponent::markCompleted);
    }

    @Override
    public boolean isCompleted() {
        return subtasks.stream().allMatch(TaskComponent::isCompleted);
    }

    @Override
    public String getDescription() {
        return description;
    }

    public List<TaskComponent> getSubtasks() {
        return subtasks;
    }
}
