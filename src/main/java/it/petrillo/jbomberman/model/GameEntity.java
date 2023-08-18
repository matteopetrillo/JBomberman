package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.model.interfaces.Renderable;
import it.petrillo.jbomberman.util.ModelObservable;

import java.awt.geom.Area;
import java.awt.image.BufferedImage;

/**
 * The GameEntity class represents a basic entity in the game, providing common properties and behaviors.
 * It extends the ModelObservable class and implements the Renderable interface.
 * Subclasses of GameEntity define specific entities with their unique characteristics.
 */
public abstract class GameEntity extends ModelObservable implements Renderable {
    protected Area collisionBox;
    protected boolean visible, toClean;
    protected int x,y,animationTick,animationIndex, animationSpeed, xCollisionOffset, yCollisionOffset;
    protected double entityScale;
    protected BufferedImage spriteSheet;
    protected BufferedImage[][] spriteAnimation;

    /**
     * Constructs a GameEntity with the specified initial position.
     *
     * @param x The X-coordinate of the entity's initial position.
     * @param y The Y-coordinate of the entity's initial position.
     */
    public GameEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X-coordinate of the entity's position.
     *
     * @return The X-coordinate of the entity's position.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y-coordinate of the entity's position.
     *
     * @return The Y-coordinate of the entity's position.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the X-coordinate of the entity's position.
     *
     * @param x The new X-coordinate to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the Y-coordinate of the entity's position.
     *
     * @param y The new Y-coordinate to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the collision box of the entity.
     *
     * @return The collision box of the entity.
     */
    public Area getCollisionBox() {
        return collisionBox;
    }

    /**
     * Returns whether the entity is currently visible on the screen.
     *
     * @return True if the entity is visible, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility status of the entity.
     *
     * @param visible True to make the entity visible, false to hide it.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Returns whether the entity needs to be cleaned (removed) from the game.
     *
     * @return True if the entity needs to be cleaned, false otherwise.
     */
    public boolean isToClean() {
        return toClean;
    }

    /**
     * Sets the flag indicating whether the entity needs to be cleaned.
     *
     * @param toClean True to indicate that the entity needs to be cleaned, false otherwise.
     */
    public void setToClean(boolean toClean) {
        this.toClean = toClean;
    }
}
