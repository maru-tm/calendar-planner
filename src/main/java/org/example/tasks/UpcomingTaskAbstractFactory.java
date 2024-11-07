package org.example.tasks;

public class UpcomingTaskAbstractFactory implements AbstractTaskFactory {
    @Override
    public TaskFactory createTaskFactory() {
        return new UpcomingTaskFactory();
    }
}
