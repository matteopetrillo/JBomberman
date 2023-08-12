package it.petrillo.jbomberman.model;

import java.awt.*;

/**
 * The Renderable interface defines the methods required for an object to be rendered on the game canvas.
 * Objects that implement this interface can be drawn, loaded with sprite images, and updated for animation.
 */
public interface Renderable {

    /**
     * Draws the object on the game canvas using the provided Graphics2D context.
     *
     * @param g The Graphics2D context used for drawing.
     */
    void draw(Graphics2D g);

    /**
     * Loads the sprite images for the object from the specified path.
     *
     * @param path The path to the sprite image resource.
     */
    void loadSprites(String path);

    /**
     * Updates the object's state, typically for animation or gameplay logic.
     * This method is called in each game frame.
     */
    void update();


}
