package it.petrillo.jbomberman.model;

/**
 * The Collidable interface defines the behavior for objects that can collide with each other.
 * Classes implementing this interface must provide the onCollision method to handle collisions.
 */
public interface Collidable {

    /**
     * Handles the collision event between this object and another Collidable object.
     *
     * @param other The other Collidable object involved in the collision.
     */
    void onCollision(Collidable other);
}
