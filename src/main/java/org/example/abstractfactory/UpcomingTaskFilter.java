package org.example.abstractfactory;

import org.example.tasks.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpcomingTaskFilter implements TaskFilter {
    @Override
    public List<Task> filter(List<Task> tasks) {
        List<Task> filteredTasks = new ArrayList<>();
        LocalDate now = LocalDate.now();  // Get the current date

        for (Task task : tasks) {
            // If the task is not completed and its due date is in the future, it's considered upcoming
            if (task.getDueDate().isAfter(now) && !task.isCompleted()) {
                filteredTasks.add(task);
            }
        }

        return filteredTasks;
    }
}
