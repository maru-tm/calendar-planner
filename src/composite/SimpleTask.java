package composite;

public class SimpleTask implements TaskComponent {
    private final String description;
    private boolean completed;

    public SimpleTask(String description) {
        this.description = description;
        this.completed = false;
    }

    @Override
    public void addTask(TaskComponent task) {
        throw new UnsupportedOperationException("Simple tasks cannot contain other tasks.");
    }

    @Override
    public void removeTask(TaskComponent task) {
        throw new UnsupportedOperationException("Simple tasks cannot contain other tasks.");
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
        return description;
    }
}
