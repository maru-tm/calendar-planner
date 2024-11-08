package flyweight;

import java.util.HashMap;
import java.util.Map;

public class TaskFlyweightFactory {
    private static final Map<String, TaskFlyweight> taskFlyweights = new HashMap<>();

    public static TaskFlyweight getFlyweight(String description) {
        taskFlyweights.putIfAbsent(description, new TaskFlyweight(description));
        return taskFlyweights.get(description);
    }
}
