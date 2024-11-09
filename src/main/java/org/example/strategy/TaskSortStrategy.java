package org.example.strategy;

import org.example.tasks.Task;

import java.util.List;

public interface TaskSortStrategy {
    // Interface method for sorting tasks
    // Each implementing class will define how to sort the tasks
    void sort(List<Task> tasks);
}
