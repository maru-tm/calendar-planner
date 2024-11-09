package src.main.java.org.example.tasks;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OverdueTaskFactory implements TaskFactory {
    @Override
    public List<Task> getTasks(Map<LocalDate, List<Task>> tasksMap, LocalDate currentDate) {
        return tasksMap.entrySet().stream()
                .filter(entry -> entry.getKey().isBefore(currentDate)) // Фильтрация просроченных задач
                .flatMap(entry -> entry.getValue().stream()) // Преобразуем список задач в поток
                .collect(Collectors.toList()); // Собираем задачи в список
    }
}
