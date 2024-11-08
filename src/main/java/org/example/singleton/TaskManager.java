package org.example.singleton;

import org.example.tasks.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {

    private static TaskManager instance;
    private Map<LocalDate, List<Task>> tasksMap;

    // Приватный конструктор для синглтона
    private TaskManager() {
        tasksMap = new HashMap<>();
    }

    // Метод для получения единственного экземпляра TaskManager
    public static TaskManager getInstance() {
        if (instance == null) {
            synchronized (TaskManager.class) {
                if (instance == null) {
                    instance = new TaskManager();
                }
            }
        }
        return instance;
    }

    // Метод для добавления задачи на определенную дату
    public void addTask(Task task) {
        LocalDate taskDate = task.getDueDate();
        tasksMap.putIfAbsent(taskDate, new ArrayList<>());
        tasksMap.get(taskDate).add(task);
    }

    // Метод для получения задач на определенную дату
    public List<Task> getTasks(LocalDate date) {
        return tasksMap.getOrDefault(date, new ArrayList<>());
    }

    // Метод для удаления всех задач на определенную дату
    public void removeTasks(LocalDate date) {
        tasksMap.remove(date);
    }

    // Метод для получения всех задач
    public Map<LocalDate, List<Task>> getAllTasksMap() {
        return tasksMap;
    }
}
