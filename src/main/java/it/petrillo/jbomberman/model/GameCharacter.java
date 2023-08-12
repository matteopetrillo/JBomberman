package it.petrillo.jbomberman.model;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameSettings.*;

/**
 * The GameCharacter abstract class represents a character entity in the game.
 * It extends the GameEntity class and implements the Collidable interface.
 * Subclasses of GameCharacter define specific characters with behavior and properties.
 */
public abstract class GameCharacter extends GameEntity implements Collidable {

    protected CollisionListener collisionListener;
    protected Direction movingDirection;
    protected boolean movingUp, movingDown, movingLeft, movingRight, hitted;
    protected int health, xSpeed, ySpeed, characterSpeed, hittedTimer;

    /**
     * Constructs a GameCharacter with the specified initial position.
     *
     * @param x The X-coordinate of the character's initial position.
     * @param y The Y-coordinate of the character's initial position.
     */
    public GameCharacter(int x, int y) {
        super(x, y);
    }

    /**
     * Determines the animation index based on the character's moving direction.
     *
     * @return The animation index corresponding to the character's moving direction.
     */
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

    /**
     * Returns the CollisionListener associated with the character.
     *
     * @return The CollisionListener associated with the character.
     */
    public CollisionListener getCollisionListener() {
        return collisionListener;
    }

    /**
     * Sets the CollisionListener for the character.
     *
     * @param collisionListener The CollisionListener to associate with the character.
     */
    public void setCollisionListener(CollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    // Getter and setter methods for moving direction and movement flags

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

    /**
     * Sets the flag indicating whether the character has been hit.
     *
     * @param hitted True if the character has been hit, false otherwise.
     */
    public void setHitted(boolean hitted) {
        this.hitted = hitted;
    }

    /**
     * Checks if the character has been hit.
     *
     * @return True if the character has been hit, false otherwise.
     */
    public boolean isHitted() {
        return hitted;
    }

    /**
     * Defines the behavior when the character is hit by a collision.
     * Subclasses should implement this method to handle character reactions.
     */
    public abstract void hitCharacter();
}
