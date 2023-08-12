package it.petrillo.jbomberman.model;

/**
 * The Movable interface represents an entity that can update its position within the game world.
 * Classes implementing this interface must provide the implementation for updating the entity's position.
 */
public interface Movable {

    /**
     * Updates the position of the movable entity based on its current movement parameters.
     * This method is responsible for determining the new position of the entity after movement.
     */
    void updatePosition();
}
