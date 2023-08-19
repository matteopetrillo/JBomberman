package it.petrillo.jbomberman.util.observer;

import it.petrillo.jbomberman.util.NotificationType;

/**
 * The ModelObserver interface defines the contract for objects that observe changes in a model.
 * Implementing classes will receive notifications when changes occur in the observed model.
 * This interface aligns with the custom implementation provided by {@link ModelObservable}
 * for better notification handling, similar to the default Java Observable class.
 */
public interface ModelObserver {

    /**
     * Called to notify the observer about a change in the observed model.
     *
     * @param notificationType The type of notification.
     * @param arg              Additional arguments related to the notification.
     */
    void update(NotificationType notificationType, Object arg);
}
