package org.example.view;

import org.example.abstractfactory.OverdueTaskFilter;
import org.example.abstractfactory.UpcomingTaskFilter;
import org.example.abstractfactory.TaskFilter;
import org.example.tasks.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FilterWindow extends JFrame {
    private List<Task> tasks;

    public FilterWindow(List<Task> tasks) {
        this.tasks = tasks;

        setTitle("Фильтры задач");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton overdueButton = new JButton("Показать просроченные задачи");
        overdueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFilteredTasks(new OverdueTaskFilter());
            }
        });

        JButton upcomingButton = new JButton("Показать предстоящие задачи");
        upcomingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFilteredTasks(new UpcomingTaskFilter());
            }
        });

        panel.add(overdueButton);
        panel.add(upcomingButton);
        add(panel);
    }

    private void showFilteredTasks(TaskFilter filter) {
        System.out.println("Все задачи перед фильтрацией: " + tasks);
        List<Task> filteredTasks = filter.filter(tasks);

        String filteredText = filteredTasks.isEmpty() ? "Нет задач, соответствующих фильтру." : filteredTasks.toString();
        JOptionPane.showMessageDialog(this, filteredText);
    }

}
