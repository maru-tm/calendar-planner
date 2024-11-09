package org.example.composite;

import java.util.ArrayList;
import java.util.List;

public class TaskGroup implements TaskComponent {
    private String groupName;
    private List<TaskComponent> tasks = new ArrayList<>();

    public TaskGroup(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public void showTask() {
        // Display the group name
        System.out.println("Task Group: " + groupName);

        // Display all tasks within this group
        for (TaskComponent task : tasks) {
            task.showTask();
        }
    }

    @Override
    public void addTask(TaskComponent task) {
        // Add a task or sub-group to this task group
        tasks.add(task);
    }

    @Override
    public void removeTask(TaskComponent task) {
        // Remove a task or sub-group from this task group
        tasks.remove(task);
    }
}
