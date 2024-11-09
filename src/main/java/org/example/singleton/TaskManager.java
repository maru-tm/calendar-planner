package org.example.singleton;

import org.example.abstractfactory.*;
import org.example.abstractfactory.TaskFilter;
import org.example.strategy.DateSortStrategy;
import org.example.strategy.TaskSortStrategy;
import org.example.tasks.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TaskManager {

    private static TaskManager instance;
    private Map<LocalDate, List<Task>> tasksMap; // Map to store tasks by due date
    private TaskSortStrategy sortStrategy; // Strategy for sorting tasks

    // Private constructor for singleton pattern
    public TaskManager() {
        tasksMap = new HashMap<>();
    }

    // Singleton pattern: ensures only one instance of TaskManager exists
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

    // Adds a task to the tasks map for a given date
    public void addTask(Task task) {
        LocalDate taskDate = task.getDueDate();
        tasksMap.putIfAbsent(taskDate, new ArrayList<>());
        tasksMap.get(taskDate).add(task);
        this.sortStrategy = new DateSortStrategy(); // Default sort strategy is by date
    }

    // Returns tasks for a specific date
    public List<Task> getTasks(LocalDate date) {
        return tasksMap.getOrDefault(date, new ArrayList<>());
    }

    // Removes all tasks for a specific date
    public void removeTasks(LocalDate date) {
        tasksMap.remove(date);
    }

    // Returns all tasks in the map
    public Map<LocalDate, List<Task>> getAllTasksMap() {
        return tasksMap;
    }

    // Filters tasks based on the given filter
    public List<Task> getTasksByFilter(TaskFilter filter) {
        List<Task> allTasks = new ArrayList<>();
        // Diagnostic output: printing tasks map content
        System.out.println("Tasks map content: " + tasksMap);

        for (List<Task> tasks : tasksMap.values()) {
            allTasks.addAll(tasks);
        }
        // Diagnostic output: printing all tasks before filtering
        System.out.println("All tasks before filtering: " + allTasks);

        return filter.filter(allTasks);
    }

    // Sets the task sorting strategy
    public void setSortStrategy(TaskSortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    // Sorts the provided tasks list using the current sorting strategy
    public List<Task> getSortedTasks(List<Task> tasks) {
        sortStrategy.sort(tasks); // Apply sorting
        return tasks;
    }
}
