package org.example.tasks;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OverdueTaskFactory implements TaskFactory {
    @Override
    public List<Task> getTasks(Map<LocalDate, List<Task>> tasksMap, LocalDate currentDate) {
        return tasksMap.entrySet().stream()
                .filter(entry -> entry.getKey().isBefore(currentDate)) // Только просроченные задачи
                .flatMap(entry -> entry.getValue().stream())
                .filter(task -> !task.isCompleted()) // Исключаем завершенные задачи
                .collect(Collectors.toList());
    }

}
