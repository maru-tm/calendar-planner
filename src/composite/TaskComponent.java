package composite;

import java.util.List;

public abstract class TaskComponent {
    private String name;
    private boolean completed;

    public TaskComponent(String name) {
        this.name = name;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Методы для работы с подзадачами
    public void add(TaskComponent task) {
        throw new UnsupportedOperationException();
    }

    public void remove(TaskComponent task) {
        throw new UnsupportedOperationException();
    }

    public List<TaskComponent> getSubTasks() {
        throw new UnsupportedOperationException();
    }

    public abstract void display();
}
