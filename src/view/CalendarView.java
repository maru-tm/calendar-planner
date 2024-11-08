package view;

import composite.CompositeTask;
import controller.CalendarController;
import composite.TaskComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CalendarView extends JFrame {
    private final JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
    private final JPanel calendarPanel = new JPanel(new GridLayout(0, 7));
    private final JLabel dateTimeLabel = new JLabel();

    public CalendarView() {
        setTitle("Task Calendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        prevButton.addActionListener(e -> navigate(-1));
        nextButton.addActionListener(e -> navigate(1));

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);

        dateTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(dateTimeLabel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);

        startDateTimeUpdater();  // Запуск обновления времени
        updateCalendar();
    }

    private void startDateTimeUpdater() {
        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("Сегодня: EEEE, d MMMM yyyy, HH:mm:ss");
            dateTimeLabel.setText(sdf.format(new Date()));  // Обновление метки времени каждую секунду
        });
        timer.start();
    }

    private void navigate(int offset) {
        CalendarController.getInstance().changeMonth(offset);
        updateCalendar();
    }

    public void updateCalendar() {
        Calendar cal = CalendarController.getInstance().getCalendar();
        monthLabel.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, getLocale()) + " " + cal.get(Calendar.YEAR));

        calendarPanel.removeAll();
        String[] daysOfWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (String day : daysOfWeek) {
            calendarPanel.add(new JLabel(day, SwingConstants.CENTER));
        }

        int startDay = cal.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < startDay; i++) {
            calendarPanel.add(new JLabel()); // Пустой элемент для выравнивания
        }

        Calendar today = Calendar.getInstance();
        int todayDay = today.get(Calendar.DAY_OF_MONTH);
        int todayMonth = today.get(Calendar.MONTH);
        int todayYear = today.get(Calendar.YEAR);

        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            int finalDay = day;
            dayButton.addActionListener(e -> showTasks(finalDay));

            if (cal.get(Calendar.MONTH) == todayMonth && cal.get(Calendar.YEAR) == todayYear && day == todayDay) {
                dayButton.setBackground(Color.CYAN);  // Выделение сегодняшней даты
            }

            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void showTasks(int day) {
        String taskKey = "Day-" + day;
        TaskComponent task = CalendarController.getInstance().getTaskForDay(day);
        boolean completed = CalendarController.getInstance().isTaskCompleted(taskKey);

        if (task != null) {
            JPanel taskPanel = new JPanel();
            taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));

            JCheckBox taskCheckbox = new JCheckBox(task.getDescription(), completed);
            taskCheckbox.addActionListener(e -> {
                boolean isSelected = taskCheckbox.isSelected();
                CalendarController.getInstance().setTaskCompleted(taskKey, isSelected);
            });
            taskPanel.add(taskCheckbox);

            if (task instanceof CompositeTask) {
                for (TaskComponent subtask : ((CompositeTask) task).getSubtasks()) {
                    JCheckBox subtaskCheckbox = new JCheckBox(subtask.getDescription(), subtask.isCompleted());
                    subtaskCheckbox.addActionListener(e -> subtask.markCompleted());
                    taskPanel.add(subtaskCheckbox);
                }
            }

            JOptionPane.showMessageDialog(this, taskPanel, "Tasks for day " + day, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No tasks for day " + day);
        }
    }
}
