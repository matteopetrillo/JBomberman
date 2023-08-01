package it.petrillo.jbomberman.model;

import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameUtils.*;

public abstract class GameCharacter extends GameEntity {

    protected CollisionListener collisionListener;
    protected Direction movingDirection;
    protected boolean movingUp, movingDown, movingLeft, movingRight, hitted;
    protected int health, xSpeed, ySpeed, characterSpeed, hittedTimer;
    protected BufferedImage[][] hittedAnimation;
    protected BufferedImage hittedSpriteSheet;
    public GameCharacter(int x, int y) {
        super(x, y);
    }

    protected int getAniIndexByDirection() {
        switch (movingDirection) {
            case UP -> {
                return 0;
            }
            case DOWN -> {
                return 2;
            }
            case LEFT -> {
                return 3;
            }
            case RIGHT -> {
                return 1;
            }
        }
        return 2;
    }


    public CollisionListener getCollisionListener() {
        return collisionListener;
    }

    public void setCollisionListener(CollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    public Direction getMovingDirection() {
        return movingDirection;
    }

    public void setMovingDirection(Direction movingDirection) {
        this.movingDirection = movingDirection;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public abstract void onCollision(GameCharacter other);
    public void setHitted(boolean hitted) {
        this.hitted = hitted;
    }
    public boolean isHitted() {
        return hitted;
    }

    public abstract void hitCharacter();
}
