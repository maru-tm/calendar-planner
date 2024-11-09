package org.example.abstractfactory;

import org.example.tasks.Task;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OverdueTaskFilter implements TaskFilter {

    @Override
    public List<Task> filter(List<Task> tasks) {
        List<Task> filteredTasks = new ArrayList<>();
        LocalDate now = LocalDate.now();  // Get the current date

        for (Task task : tasks) {
            // If the task is not completed and its due date has passed, it is overdue
            if (task.getDueDate().isBefore(now) && !task.isCompleted()) {
                filteredTasks.add(task);  // Add the overdue task to the list
            }
        }

        return filteredTasks;  // Return the filtered list of overdue tasks
    }
}
