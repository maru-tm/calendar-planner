package src.main.java.org.example.tasks;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TaskFactory {
    List<Task> getTasks(Map<LocalDate, List<Task>> tasksMap, LocalDate currentDate);
}
