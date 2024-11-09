package composite;

import java.util.List;

public class SimpleTask extends TaskComponent {

    public SimpleTask(String name) {
        super(name);
    }

    @Override
    public void display() {
        System.out.println(getName() + (isCompleted() ? " [Выполнено]" : " [Не выполнено]"));
    }

    @Override
    public void add(TaskComponent task) {
        throw new UnsupportedOperationException("SimpleTask не может иметь подзадач.");
    }

    @Override
    public void remove(TaskComponent task) {
        throw new UnsupportedOperationException("SimpleTask не может иметь подзадач.");
    }

    @Override
    public List<TaskComponent> getSubTasks() {
        throw new UnsupportedOperationException("SimpleTask не может иметь подзадач.");
    }
}
