package it.petrillo.jbomberman.model;


import java.util.List;
import java.util.Random;

import static it.petrillo.jbomberman.util.GameSettings.*;

/**
 * The Enemy abstract class serves as a base for enemy characters in the game.
 * It extends the GameCharacter class and implements the Movable and Renderable interfaces.
 * This class encapsulates common behavior and attributes for enemy characters.
 */
public abstract class Enemy extends GameCharacter implements Movable, Renderable {

    /**
     * Constructs an Enemy instance with the specified initial position.
     *
     * @param x The X-coordinate of the enemy's initial position.
     * @param y The Y-coordinate of the enemy's initial position.
     */
    public Enemy(int x, int y) {
        super(x, y);
        movingDirection = pickRandomDirection();
        visible = true;
    }

    /**
     * Picks a random movement direction for the enemy.
     *
     * @return A randomly chosen Direction for movement.
     */
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

    /**
     * Updates the position of the enemy based on its current direction and collision conditions.
     */
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

    /**
     * Changes the enemy's direction to an available direction other than the current one.
     */
    private void changeDirection() {
        List<Direction> availableDirections = collisionListener.getAvailableDirections(characterSpeed,collisionBox);
        Random rd = new Random();
        int n = rd.nextInt(0,availableDirections.size());
        movingDirection = availableDirections.get(n);
        setFlagFromDirection();
    }

    /**
     * Calculates the change in speed based on the current movement direction.
     *
     * @return An array containing the change in X-speed and Y-speed.
     */
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

    /**
     * Inverts the enemy's direction, used for collision handling.
     *
     * @param direction The current direction to be inverted.
     */
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

    /**
     * Sets the movement flags based on the current movement direction.
     */
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

    /**
     * Handles the effect of being hit by a collision or a bomb.
     * Decreases the enemy's health and manages the hit effect timer.
     */
    @Override
    public void hitCharacter() {
        if (hittedTimer <= 0) {
            health--;
            hittedTimer = 60;
            hitted = false;
        }
    }

    /**
     * Handles collision events with other Collidable objects.
     * Changes direction when colliding with the player and inverts direction when colliding with another enemy.
     */
    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Bomberman)
            changeDirection();
        if (other instanceof Enemy)
            invertDirection(movingDirection);
    }
}
