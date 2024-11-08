package org.example.flyweight;

import java.util.HashMap;
import java.util.Map;

public class TaskFlyweightFactory {
    private static final Map<String, flyweight.TaskFlyweight> taskFlyweights = new HashMap<>();

    public static flyweight.TaskFlyweight getFlyweight(String description) {
        taskFlyweights.putIfAbsent(description, new flyweight.TaskFlyweight(description));
        return taskFlyweights.get(description);
    }
}
