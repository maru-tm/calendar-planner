package org.example.abstractfactory;

import org.example.tasks.Task;
import java.util.List;

public abstract class AbstractTaskFilter implements TaskFilter {

    // This method applies the filter to a list of tasks
    @Override
    public List<Task> filter(List<Task> tasks) {
        return applyFilter(tasks);  // Delegate the actual filtering to the concrete subclass
    }

    // Abstract method for applying the filter (must be implemented by subclasses)
    protected abstract List<Task> applyFilter(List<Task> tasks);
}
