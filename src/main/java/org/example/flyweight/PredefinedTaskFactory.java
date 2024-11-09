package org.example.flyweight;

import org.example.tasks.Task;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
public class PredefinedTaskFactory {
    private static final Map<String, Task> predefinedTasks = new HashMap<>();

    static {
        // Predefine some common tasks for quick access
        predefinedTasks.put("Уборка дома", new Task("Уборка дома", LocalDate.now().plusDays(1)));
        predefinedTasks.put("Сходить в магазин", new Task("Сходить в магазин", LocalDate.now().plusDays(3)));
        predefinedTasks.put("Написать отчет", new Task("Написать отчет", LocalDate.now().plusDays(5)));
    }

    // Метод для получения задачи по описанию
    public static Task getTask(String description) {
        return predefinedTasks.get(description);
    }

    // Метод для получения всех предустановленных задач
    public static Map<String, Task> getAllPredefinedTasks() {
        return predefinedTasks;
    }
}
