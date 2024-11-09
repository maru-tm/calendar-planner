package org.example.abstractfactory;

import org.example.tasks.Task;

import java.util.List;

public abstract class AbstractTaskFilter implements TaskFilter {
    @Override
    public List<Task> filter(List<Task> tasks) {
        return applyFilter(tasks);
    }

    protected abstract List<Task> applyFilter(List<Task> tasks);
}