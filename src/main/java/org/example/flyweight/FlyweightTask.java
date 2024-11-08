package org.example.flyweight;

import org.example.composite.TaskComponent;

public class FlyweightTask implements TaskComponent {
    private final flyweight.TaskFlyweight taskFlyweight;
    private boolean completed;

    public FlyweightTask(flyweight.TaskFlyweight taskFlyweight) {
        this.taskFlyweight = taskFlyweight;
        this.completed = false;
    }

    @Override
    public void addTask(TaskComponent task) {
        throw new UnsupportedOperationException("Flyweight tasks cannot contain other tasks.");
    }

    @Override
    public void removeTask(TaskComponent task) {
        throw new UnsupportedOperationException("Flyweight tasks cannot contain other tasks.");
    }

    @Override
    public void markCompleted() {
        this.completed = true;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String getDescription() {
        return taskFlyweight.getDescription();
    }
}
