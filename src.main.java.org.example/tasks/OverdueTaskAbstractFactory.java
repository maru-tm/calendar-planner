package src.main.java.org.example.tasks;

public class OverdueTaskAbstractFactory implements AbstractTaskFactory {
    @Override
    public TaskFactory createTaskFactory() {
        return new OverdueTaskFactory();
    }
}
