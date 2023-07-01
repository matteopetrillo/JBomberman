package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.CustomObserver;
import it.petrillo.jbomberman.util.NotificationType;
import it.petrillo.jbomberman.util.Position;

public class Player extends GameEntity {

    private static Player playerInstance;
    String playerName;
    private Position position;
    private Player(int x, int y) {
        super(x, y);
        position = new Position(x,y);
        super.setVisible(true);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void addObserver(CustomObserver observer) {
        super.addObserver(observer);
    }

    @Override
    public void removeObserver(CustomObserver observer) {
        super.removeObserver(observer);
    }

    @Override
    public void notifyObservers(NotificationType notificationType, Object arg) {
        super.notifyObservers(notificationType, arg);
    }

    public static Player getPlayerInstance() {
        if(playerInstance == null) {
            playerInstance = new Player(50,50);
        }
        return playerInstance;
    }
    @Override
    public void onCollision(Collidable other) {

    }
}
