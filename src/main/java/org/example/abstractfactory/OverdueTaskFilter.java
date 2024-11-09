package org.example.abstractfactory;

import org.example.tasks.Task;

import java.util.ArrayList;
import java.util.List;


public class OverdueTaskFilter implements TaskFilter {
    @Override
    public List<Task> filter(List<Task> tasks) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            System.out.println("Проверка задачи (Просроченные): " + task.getDescription() + " | Due: " + task.getDueDate() + " | Просрочена: " + task.isOverdue());
            if (task.isOverdue()) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }
}