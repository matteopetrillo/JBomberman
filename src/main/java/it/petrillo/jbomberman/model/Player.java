package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.Settings;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class Player extends GameEntity {

    private static Player playerInstance;
    String playerName;
    private int xSpeed, ySpeed, playerSpeed;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private final int xOffset = 30;
    private final int yOffset = 50;
    private int health = 5;
    private Direction actualDirection = Direction.DOWN;

    private Player(int x, int y, boolean visibility) {
        super(x, y, visibility);
        collisionBox.setLocation(super.x+xOffset, super.y+yOffset);
        collisionBox.setSize((int) (16*Settings.SCALE), (int) (16*Settings.SCALE));
        this.playerSpeed = 4;
    }

    public void updateStatus() {
        updatePosition();
    }
    public void updatePosition() {
        xSpeed = 0;
        ySpeed = 0;

        if (movingUp) {
            ySpeed = -playerSpeed;
            actualDirection = Direction.UP;
        }
        else if (movingDown) {
            ySpeed = playerSpeed;
            actualDirection = Direction.DOWN;
        }
        else if (movingLeft) {
            xSpeed = -playerSpeed;
            actualDirection = Direction.LEFT;
        }
        else if (movingRight) {
            xSpeed = playerSpeed;
            actualDirection = Direction.RIGHT;
        }

        if(collisionListener != null && collisionListener.canMoveThere(xSpeed, ySpeed,collisionBox)) {
            super.x += xSpeed;
            super.y += ySpeed;
            collisionBox.setLocation(super.x+xOffset, super.y+yOffset);
        }
    }

    public void dropBomb() {
        notifyObservers((NotificationType.DROP_BOMB), this);
    }

    public void hitPlayer() {
        System.out.println(health);
        health--;
        System.out.println(health);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public static Player getPlayerInstance() {
        if(playerInstance == null) {
            playerInstance = new Player((int) (52*Settings.SCALE), (int) (24*Settings.SCALE), true);
        }
        return playerInstance;
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

    public Direction getActualDirection() {
        return actualDirection;
    }
}
