package com.calendarapp.model.observer;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<EventObserver> observers = new ArrayList<>();

    public void addObserver(EventObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String data) {
        for (EventObserver observer : observers) {
            observer.update(data);
        }
    }
}
