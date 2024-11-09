package view;

import composite.TaskComponent;
import flyweight.TaskFlyweightFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class CalendarView extends JFrame {
    private JPanel calendarPanel;
    private JLabel currentDateLabel;
    private LocalDate currentDate;
    private YearMonth displayedMonth;
    private Map<LocalDate, Map<TaskComponent, Boolean>> taskStatusMap;
    private JLabel monthYearLabel;

    public CalendarView() {
        setTitle("Календарь задач");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        taskStatusMap = new HashMap<>();
        currentDate = LocalDate.now();
        displayedMonth = YearMonth.now();

        currentDateLabel = new JLabel("Сегодня " + currentDate);
        calendarPanel = new JPanel(new GridLayout(0, 7));

        JButton prevMonthButton = new JButton("<");
        JButton nextMonthButton = new JButton(">");
        prevMonthButton.addActionListener(e -> navigateMonth(-1));
        nextMonthButton.addActionListener(e -> navigateMonth(1));

        JPanel navigationPanel = new JPanel();
        navigationPanel.add(prevMonthButton);

        // Метка месяца и года
        monthYearLabel = new JLabel(displayedMonth.getMonth() + " " + displayedMonth.getYear(), SwingConstants.CENTER);
        navigationPanel.add(monthYearLabel);
        navigationPanel.add(nextMonthButton);

        initializeCalendar(); // Инициализация календаря

        add(currentDateLabel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    private void initializeCalendar() {
        calendarPanel.removeAll();

        // Названия дней недели с понедельника
        String[] daysOfWeek = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        for (String day : daysOfWeek) {
            calendarPanel.add(new JLabel(day, SwingConstants.CENTER));
        }

        // Первый день месяца
        LocalDate firstDayOfMonth = displayedMonth.atDay(1);
        int dayOfWeekOffset = (firstDayOfMonth.getDayOfWeek().getValue() + 6) % 7; // сдвиг на понедельник

        // Заполняем пустые ячейки для дней до начала месяца
        for (int i = 0; i < dayOfWeekOffset; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // Отображаем дни месяца
        for (int day = 1; day <= displayedMonth.lengthOfMonth(); day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            LocalDate date = displayedMonth.atDay(day);

            // Подсветить текущий день
            if (date.equals(currentDate)) {
                dayButton.setBackground(Color.CYAN);
            }

            // Обработчик нажатия на день
            dayButton.addActionListener(e -> showTasksForDate(date));
            calendarPanel.add(dayButton);
        }

        // Перерисовываем панель
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void navigateMonth(int offset) {
        // Изменение месяца
        displayedMonth = displayedMonth.plusMonths(offset);
        monthYearLabel.setText(displayedMonth.getMonth() + " " + displayedMonth.getYear()); // Обновление метки месяца и года
        initializeCalendar();
    }

    private void showTasksForDate(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) { // Суббота
            TaskComponent cleaningTask = TaskFlyweightFactory.getCleaningTask();
            displayTaskDialog(date, cleaningTask);
        } else {
            JOptionPane.showMessageDialog(this, "Нет задач на этот день.");
        }
    }

    private void displayTaskDialog(LocalDate date, TaskComponent task) {
        JDialog taskDialog = new JDialog(this, task.getName(), true);
        taskDialog.setSize(400, 400);
        taskDialog.setLayout(new BorderLayout());

        // Панель с подзадачами
        JPanel subTaskPanel = new JPanel();
        subTaskPanel.setLayout(new BoxLayout(subTaskPanel, BoxLayout.Y_AXIS));

        // Сохранение состояния подзадач
        Map<TaskComponent, Boolean> taskStatus = taskStatusMap.getOrDefault(date, new HashMap<>());
        for (TaskComponent subTask : task.getSubTasks()) {
            JCheckBox checkBox = new JCheckBox(subTask.getName(), taskStatus.getOrDefault(subTask, false));
            checkBox.addActionListener(e -> {
                taskStatus.put(subTask, checkBox.isSelected());
                taskStatusMap.put(date, taskStatus);  // Сохранение статуса подзадачи
            });
            subTaskPanel.add(checkBox);
        }

        // Кнопка для отображения/скрытия подзадач
        JButton toggleButton = new JButton("Показать подзадачи");
        toggleButton.addActionListener(e -> {
            subTaskPanel.setVisible(!subTaskPanel.isVisible());
            toggleButton.setText(subTaskPanel.isVisible() ? "Скрыть подзадачи" : "Показать подзадачи");
            taskDialog.revalidate();
        });

        taskDialog.add(toggleButton, BorderLayout.NORTH);
        taskDialog.add(subTaskPanel, BorderLayout.CENTER);

        // Кнопка "Закрыть"
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> taskDialog.dispose());
        taskDialog.add(closeButton, BorderLayout.SOUTH);

        taskDialog.setVisible(true);
    }
}
