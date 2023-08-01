package it.petrillo.jbomberman.model;


import java.util.List;
import java.util.Random;

import static it.petrillo.jbomberman.util.GameUtils.*;

public abstract class Enemy extends GameCharacter implements Movable,Animatable {
    public Enemy(int x, int y) {
        super(x, y);
        movingDirection = pickRandomDirection();
    }

    protected Direction pickRandomDirection() {
        Random rd = new Random();
        int n = rd.nextInt(1,4);
        switch (n) {
            case 1 -> {
                return Direction.UP;
            }
            case 2 -> {
                return Direction.DOWN;
            }
            case 3 -> {
                return Direction.LEFT;
            }
            case 4 -> {
                return Direction.RIGHT;
            }
        }
        return null;
    }

    @Override
    public void updatePosition() {
        setFlagFromDirection();
        int[] deltaSpeed = getDeltaSpeedByDirection();
        xSpeed = deltaSpeed[0];
        ySpeed = deltaSpeed[1];
        if(!collisionListener.canMoveThere(xSpeed, ySpeed,collisionBox)) {
            changeDirection();
            int[] deltas = getDeltaSpeedByDirection();
            xSpeed = deltas[0];
            ySpeed = deltas[1];
        }
        super.x += xSpeed;
        super.y += ySpeed;
        collisionBox.setLocation(super.x+xCollisionOffset, super.y+yCollisionOffset);
    }

    private void changeDirection() {
        List<Direction> availableDirections = collisionListener.getAvailableDirections(characterSpeed,collisionBox);
        Random rd = new Random();
        int n = rd.nextInt(0,availableDirections.size());
        movingDirection = availableDirections.get(n);
        setFlagFromDirection();
    }
    private int[] getDeltaSpeedByDirection() {
        int[] deltaSpeed = new int[2];
        xSpeed = 0;
        ySpeed = 0;

        if (movingUp) {
            ySpeed = -characterSpeed;
        }
        else if (movingDown) {
            ySpeed = characterSpeed;
        }
        else if (movingLeft) {
            xSpeed = -characterSpeed;
        }
        else if (movingRight) {
            xSpeed = characterSpeed;
        }
        deltaSpeed[0] = xSpeed;
        deltaSpeed[1] = ySpeed;

        return deltaSpeed;
    }

    private void invertDirection(Direction direction) {
        switch (direction) {
            case UP -> {
                movingDirection = Direction.DOWN;
            }
            case DOWN -> {
                movingDirection = Direction.UP;
            }
            case LEFT -> {
                movingDirection = Direction.RIGHT;
            }
            case RIGHT -> {
                movingDirection = Direction.LEFT;
            }
        }
        setFlagFromDirection();
    }
    private void setFlagFromDirection() {
        movingLeft = false;
        movingRight = false;
        movingDown = false;
        movingUp = false;

        switch (movingDirection) {
            case UP -> {
                movingUp = true;
            }
            case DOWN -> {
                movingDown = true;
            }
            case LEFT -> {
                movingLeft = true;
            }
            case RIGHT -> {
                movingRight = true;
            }
        }
    }

    @Override
    public void onCollision(GameCharacter other) {
        if (other instanceof Bomberman)
            changeDirection();
    }
}
