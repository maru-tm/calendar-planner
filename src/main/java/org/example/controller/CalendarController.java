package com.calendarapp.controller;

import com.calendarapp.model.CalendarModel;
import com.calendarapp.model.Event;
import com.calendarapp.view.CalendarView;

public class CalendarController {
    private CalendarModel model;
    private CalendarView view;

    public CalendarController(CalendarModel model, CalendarView view) {
        this.model = model;
        this.view = view;
    }

    public void addEvent(Event event) {
        model.addEvent(event);
        view.updateCalendar();
    }

    public void removeEvent(Event event) {
        model.removeEvent(event);
        view.updateCalendar();
    }
}
