package org.example.strategy;

import org.example.tasks.Task;
import java.util.List;

public class DescriptionSortStrategy implements TaskSortStrategy {
    // Sorts the list of tasks by their description in alphabetical order
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort((task1, task2) -> task1.getDescription().compareTo(task2.getDescription()));
    }
}
