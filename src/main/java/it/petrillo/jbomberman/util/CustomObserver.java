package it.petrillo.jbomberman.util;

public interface CustomObserver {
    void update(GameSettings.NotificationType notificationType, Object arg);
}
