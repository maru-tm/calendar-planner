package org.example.abstractfactory;


import org.example.tasks.Task;

import java.util.List;

public interface TaskFilter {
    List<Task> filter(List<Task> tasks); // Используем метод `filter` вместо `filterTasks`
}