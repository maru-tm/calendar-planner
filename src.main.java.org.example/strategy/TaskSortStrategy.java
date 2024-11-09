package src.main.java.org.example.strategy;

import src.main.java.org.example.tasks.Task;
import java.util.List;

public interface TaskSortStrategy {
    void sort(List<Task> tasks);
}
