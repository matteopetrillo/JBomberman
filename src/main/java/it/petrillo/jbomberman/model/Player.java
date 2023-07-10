package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.SenderType;
import it.petrillo.jbomberman.util.Settings;

public class Player extends GameEntity {

    private static Player playerInstance;
    String playerName;
    private int xSpeed, ySpeed, playerSpeed;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private Player(int x, int y, boolean visibility) {
        super(x, y, visibility);
        collisionBox.setLocation(super.x+Settings.TILE_SIZE/2, super.y+18+Settings.TILE_SIZE/2);
        collisionBox.setSize((int) (16*Settings.SCALE), (int) (16*Settings.SCALE));
        this.playerSpeed = 4;
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
            collisionBox.setLocation(super.x+Settings.TILE_SIZE/2, super.y+18+Settings.TILE_SIZE/2);
        }
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public static Player getPlayerInstance() {
        if(playerInstance == null) {
            playerInstance = new Player(2*Settings.TILE_SIZE,Settings.TILE_SIZE, true);
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
