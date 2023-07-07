package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.controller.CollisionManager;
import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.SenderType;
import it.petrillo.jbomberman.util.GameUtils.ObjectVisibility;
import it.petrillo.jbomberman.util.Settings;

import java.awt.*;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class Player extends GameEntity {

    private static Player playerInstance;
    String playerName;
    private int xSpeed, ySpeed, playerSpeed;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private Player(int x, int y, ObjectVisibility visible) {
        super(x, y, visible);
        this.playerSpeed = 5;
    }

    public void updateStatus() {
        updatePosition();
        notifyObservers(SenderType.PLAYER, this);
    }
    public void updatePosition() {
        xSpeed = 0;
        ySpeed = 0;

        if (movingUp)
            ySpeed = -playerSpeed;
        else if (movingDown)
            ySpeed = playerSpeed;
        else if (movingLeft)
            xSpeed = -playerSpeed;
        else if (movingRight)
            xSpeed = playerSpeed;

        if(collisionListener != null && collisionListener.canMoveThere(xSpeed, ySpeed,collisionBox)) {
            super.x += xSpeed;
            super.y += ySpeed;
            collisionBox.setLocation(super.x, super.y);
        }
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public static Player getPlayerInstance() {
        if(playerInstance == null) {
            playerInstance = new Player(3*Settings.TILE_SIZE,3*Settings.TILE_SIZE, ObjectVisibility.VISIBLE);
        }
        return playerInstance;
    }
    @Override
    public void onCollision(Collidable other) {

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
