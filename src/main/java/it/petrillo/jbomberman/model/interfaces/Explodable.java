package it.petrillo.jbomberman.model.interfaces;

/**
 * The Explodable interface defines methods for objects that can undergo an explosion.
 * Classes implementing this interface provide methods to indicate and check the explosion state.
 */
public interface Explodable {

    /**
     * Sets the explosion state of the object.
     *
     * @param exploding True if the object is exploding, false otherwise.
     */
    void setExploding(boolean exploding);

    /**
     * Checks whether the object is currently in an explosion state.
     *
     * @return True if the object is exploding, false otherwise.
     */
    boolean isExploding();
}
