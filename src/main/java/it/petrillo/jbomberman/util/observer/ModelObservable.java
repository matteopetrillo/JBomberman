package it.petrillo.jbomberman.util.observer;

import it.petrillo.jbomberman.util.NotificationType;

import java.util.ArrayList;
import java.util.List;

/**
 * The ModelObservable class provides a custom implementation of the Observer pattern
 * for better notification handling, similar to the default Java Observable class
 * with some differences in method parameters.
 */
public class ModelObservable {

    private final List<ModelObserver> observers = new ArrayList<>();


    /**
     * Adds an observer to the list of registered observers.
     *
     * @param observer The observer to be added.
     */
    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }


    /**
     * Removes an observer from the list of registered observers.
     *
     * @param observer The observer to be removed.
     */
    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }


    /**
     * Notifies all registered observers with the specified notification type and arguments.
     *
     * @param notificationType The type of notification.
     * @param arg              The arguments to pass to observers.
     */
    public void notifyObservers(NotificationType notificationType, Object arg) {
        for (ModelObserver observer : observers) {
            observer.update(notificationType, arg);
        }
    }
}
