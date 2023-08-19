package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.util.Direction;

import java.awt.geom.Area;
import java.util.List;

/**
 * The CollisionListener interface defines methods for checking tile collision related operations.
 * Classes implementing this interface provide methods to determine if a character can move and available directions
 * for enemy AI.
 */
public interface CollisionListener {

    /**
     * Checks whether an entity can move to the specified position without colliding with obstacles.
     *
     * @param dx           The change in X-coordinate for the movement.
     * @param dy           The change in Y-coordinate for the movement.
     * @param collisionBox The collision box of the entity.
     * @return True if the entity can move to the specified position, otherwise false.
     */
    boolean canMoveThere(int dx, int dy, Area collisionBox);

    /**
     * Returns a list of available directions for movement based on speed and collision box.
     *
     * @param speed        The speed of movement.
     * @param collisionBox The collision box of the entity.
     * @return A list of available directions for movement.
     */
    List<Direction> getAvailableDirections(int speed, Area collisionBox);
}
