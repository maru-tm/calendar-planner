package org.example.strategy;

import org.example.tasks.Task;
import java.util.List;

public class DateSortStrategy implements TaskSortStrategy {
    // Sorts the list of tasks by their due date in ascending order
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort((task1, task2) -> task1.getDueDate().compareTo(task2.getDueDate()));
    }
}
