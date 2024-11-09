package flyweight;

import composite.TaskComponent;
import composite.CompositeTask;
import composite.SimpleTask;

public class TaskFlyweightFactory {
    private static TaskComponent cleaningTask;

    public static TaskComponent getCleaningTask() {
        if (cleaningTask == null) {
            // Создание задачи уборки, которая будет повторяться каждую субботу
            cleaningTask = new CompositeTask("Уборка");

            // Добавляем подзадачи
            cleaningTask.add(new SimpleTask("Помыть полы"));
            cleaningTask.add(new SimpleTask("Вынести мусор"));
            cleaningTask.add(new SimpleTask("Протереть пыль"));
        }
        return cleaningTask;
    }
}
