package com.calendarapp;

import com.calendarapp.controller.CalendarController;
import com.calendarapp.model.CalendarModel;
import com.calendarapp.view.CalendarView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        CalendarModel model = new CalendarModel();
        CalendarView view = new CalendarView(primaryStage);
        CalendarController controller = new CalendarController(model, view);

        view.setController(controller);
        view.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
