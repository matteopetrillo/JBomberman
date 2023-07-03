package it.petrillo.jbomberman.util;

import java.util.ArrayList;
import java.util.List;

public class CustomObservable {
    private List<CustomObserver> observers;

    public CustomObservable() {
        observers = new ArrayList<>();
    }

    public void addObserver(CustomObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CustomObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(SenderType senderType, Object arg) {
        for (CustomObserver observer : observers) {
            observer.update(senderType, arg);
        }
    }
}
