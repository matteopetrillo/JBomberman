package it.petrillo.jbomberman.model;

import java.awt.*;
import static it.petrillo.jbomberman.util.GameSettings.*;

/**
 * The MapTile class represents a tile within the game map grid. It contains information about the tile's
 * position, ID, walkability, and collision box.
 */
public class MapTile {
    private Rectangle collisionBox;
    private int x, y, tileId;
    private boolean isWalkable;

    /**
     * Creates a new MapTile instance with the specified position, tile ID, and walkability status.
     *
     * @param x         The x-coordinate of the tile.
     * @param y         The y-coordinate of the tile.
     * @param tileId    The ID of the tile.
     * @param isWalkable Determines whether the tile is walkable or not.
     */
    public MapTile(int x, int y, int tileId, boolean isWalkable) {
        this.x = x;
        this.y = y;
        this.tileId = tileId;
        this.isWalkable = isWalkable;
        collisionBox = new Rectangle(x,y, TILE_SIZE, TILE_SIZE);
    }

    /**
     * Returns the ID of the tile.
            *
            * @return The ID of the tile.
     */
    public int getTileId() {
        return tileId;
    }

    /**
     * Sets the ID of the tile.
     *
     * @param tileId The new ID to set for the tile.
     */
    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    /**
     * Returns the collision box associated with the tile.
     *
     * @return The collision box of the tile.
     */
    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    /**
     * Sets the collision box for the tile.
     *
     * @param collisionBox The new collision box to set.
     */
    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    /**
     * Returns the x-coordinate of the tile.
     *
     * @return The x-coordinate of the tile.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the tile.
     *
     * @param x The new x-coordinate to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the tile.
     *
     * @return The y-coordinate of the tile.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the tile.
     *
     * @param y The new y-coordinate to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns whether the tile is walkable.
     *
     * @return `true` if the tile is walkable, otherwise `false`.
     */
    public boolean isWalkable() {
        return isWalkable;
    }

    /**
     * Sets the walkability status of the tile.
     *
     * @param walkable The new walkability status to set.
     */
    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }
}
