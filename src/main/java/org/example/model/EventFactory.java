package com.calendarapp.model;

public class EventFactory {
    public static Event createEvent(String title, String date) {
        return new Event(title, date);
    }
}
