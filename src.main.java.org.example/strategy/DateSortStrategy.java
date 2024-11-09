package src.main.java.org.example.strategy;

import src.main.java.org.example.tasks.Task;
import java.util.List;

public class DateSortStrategy implements TaskSortStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort((task1, task2) -> task1.getDueDate().compareTo(task2.getDueDate()));
    }
}
