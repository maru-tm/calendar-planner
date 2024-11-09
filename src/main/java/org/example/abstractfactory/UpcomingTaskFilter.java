package org.example.abstractfactory;

import org.example.tasks.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class UpcomingTaskFilter implements TaskFilter {
    @Override
    public List<Task> filter(List<Task> tasks) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            System.out.println("Проверка задачи (Предстоящие): " + task.getDescription() + " | Due: " + task.getDueDate() + " | Предстоящая: " + task.isUpcoming());
            if (task.isUpcoming()) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }
}