package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.CustomObserver;
import it.petrillo.jbomberman.util.SenderType;
import it.petrillo.jbomberman.util.Position;
import it.petrillo.jbomberman.util.GameUtils.ObjectVisibility;

public class Player extends GameEntity {

    private static Player playerInstance;
    String playerName;
    private Position position;
    private int x, y;
    private int velocity;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private Player(int x, int y, ObjectVisibility visible) {
        super(x, y, visible);
        this.x = x;
        this.y = y;
        this.velocity = 4;
    }

    public void updateStatus() {
        updatePosition();
        notifyObservers(SenderType.PLAYER, this);
    }
    public void updatePosition() {
        if (movingUp) y -= velocity;
        else if (movingDown) y += velocity;
        else if (movingRight) x += velocity;
        else if (movingLeft) x -= velocity;
    }


    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Player getPlayerInstance() {
        if(playerInstance == null) {
            playerInstance = new Player(50,50, ObjectVisibility.VISIBLE);
        }
        return playerInstance;
    }
    @Override
    public void onCollision(Collidable other) {

    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }
    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
}
