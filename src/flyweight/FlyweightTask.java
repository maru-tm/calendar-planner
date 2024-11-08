package flyweight;

import composite.TaskComponent;

public class FlyweightTask implements TaskComponent {
    private final TaskFlyweight taskFlyweight;
    private boolean completed;

    public FlyweightTask(TaskFlyweight taskFlyweight) {
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
