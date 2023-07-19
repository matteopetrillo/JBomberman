package it.petrillo.jbomberman.util;

public interface CustomObserver {
    void update(GameUtils.NotificationType notificationType, Object arg);
}
