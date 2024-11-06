package com.calendarapp.view;

import com.calendarapp.controller.CalendarController;
import com.calendarapp.model.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CalendarView {
    private Stage stage;
    private CalendarController controller;

    public CalendarView(Stage stage) {
        this.stage = stage;
    }

    public void setController(CalendarController controller) {
        this.controller = controller;
    }

    public void show() {
        stage.setTitle("Calendar App");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();
        Label dateLabel = new Label("Date:");
        TextField dateField = new TextField();

        Button addButton = new Button("Add Event");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String date = dateField.getText();
            controller.addEvent(new Event(title, date));
        });

        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleField, 1, 0);
        gridPane.add(dateLabel, 0, 1);
        gridPane.add(dateField, 1, 1);
        gridPane.add(addButton, 1, 2);

        Scene scene = new Scene(gridPane, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    public void updateCalendar() {
        // Logic for calendar updates
    }
}
