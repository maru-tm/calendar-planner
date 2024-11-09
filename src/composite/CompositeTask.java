package composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeTask extends TaskComponent {
    private List<TaskComponent> subTasks;

    public CompositeTask(String name) {
        super(name);
        this.subTasks = new ArrayList<>();
    }

    @Override
    public void add(TaskComponent task) {
        subTasks.add(task);
    }

    @Override
    public void remove(TaskComponent task) {
        subTasks.remove(task);
    }

    @Override
    public List<TaskComponent> getSubTasks() {
        return subTasks;
    }

    @Override
    public void display() {
        System.out.println(getName() + (isCompleted() ? " [Выполнено]" : " [Не выполнено]"));
        for (TaskComponent subTask : subTasks) {
            subTask.display();
        }
    }
}
