package controller;

import composite.TaskCalendar;
import composite.TaskComponent;
import composite.CompositeTask;
import composite.SimpleTask;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class CalendarController {
    private static final CalendarController instance = new CalendarController();
    private final Calendar calendar;
    private final TaskCalendar taskCalendar;
    private final Map<String, Boolean> taskStatusMap = new HashMap<>();
    private static final String SAVE_FILE = "tasks.dat";

    private CalendarController() {
        calendar = Calendar.getInstance();
        taskCalendar = new TaskCalendar();

        loadTaskStatus();

        // Создаем задачу "Уборка" с подзадачами
        CompositeTask cleaningTask = new CompositeTask("Уборка");
        cleaningTask.addTask(new SimpleTask("Помыть полы"));
        cleaningTask.addTask(new SimpleTask("Вынести мусор"));

        assignCleaningTaskToSaturdays(cleaningTask);  // Назначаем задачу "Уборка" на все субботы месяца
    }

    // Метод для назначения задачи "Уборка" на все субботы текущего месяца
    private void assignCleaningTaskToSaturdays(CompositeTask cleaningTask) {
        Calendar tempCalendar = (Calendar) calendar.clone();
        int daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= daysInMonth; i++) {
            tempCalendar.set(Calendar.DAY_OF_MONTH, i);
            if (tempCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                String taskKey = "Day-" + i;
                taskCalendar.addTask(taskKey, cleaningTask);  // Добавляем задачу на субботы
            }
        }
    }

    public static CalendarController getInstance() {
        return instance;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public Date getCurrentDate() {
        return calendar.getTime();
    }

    public void changeMonth(int offset) {
        calendar.add(Calendar.MONTH, offset);
        taskCalendar.clearTasks();  // Очищаем задачи и переназначаем для новой даты
        CompositeTask cleaningTask = new CompositeTask("Уборка");
        cleaningTask.addTask(new SimpleTask("Помыть полы"));
        cleaningTask.addTask(new SimpleTask("Вынести мусор"));
        assignCleaningTaskToSaturdays(cleaningTask);  // Обновляем задачи для новой даты
    }

    public TaskComponent getTaskForDay(int day) {
        return taskCalendar.getTask("Day-" + day);
    }

    public boolean isTaskCompleted(String taskKey) {
        return taskStatusMap.getOrDefault(taskKey, false);
    }

    public void setTaskCompleted(String taskKey, boolean completed) {
        taskStatusMap.put(taskKey, completed);
        saveTaskStatus();
    }

    private void loadTaskStatus() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            taskStatusMap.putAll((Map<String, Boolean>) ois.readObject());
        } catch (Exception e) {
            System.out.println("Нет сохраненных данных о задачах. Начинаем с чистого листа.");
        }
    }

    private void saveTaskStatus() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(taskStatusMap);
        } catch (IOException e) {
            System.err.println("Не удалось сохранить состояние задач.");
        }
    }
}
