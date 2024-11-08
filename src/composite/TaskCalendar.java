package composite;

import java.util.HashMap;
import java.util.Map;

public class TaskCalendar {
    private final Map<String, TaskComponent> tasks = new HashMap<>();

    public void addTask(String date, TaskComponent task) {
        tasks.put(date, task);
    }

    public TaskComponent getTask(String date) {
        return tasks.get(date);
    }

    public void clearTasks() {

    }
}
