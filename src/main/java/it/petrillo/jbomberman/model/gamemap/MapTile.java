package it.petrillo.jbomberman.model.gamemap;



import java.awt.*;
import static it.petrillo.jbomberman.util.GameConstants.*;

/**
 * The MapTile class represents a tile within the game map grid. It contains information about the tile's
 * position, ID, walkability, and collision box.
 */
public class MapTile {

    public enum TileType {
        WALL, FLOOR
    }


    private Rectangle collisionBox;
    private int x, y;
    private boolean isWalkable;
    private final TileType tileType;

    /**
     * Creates a new MapTile instance with the specified position, tile ID, and walkability status.
     *
     * @param x          The x-coordinate of the tile.
     * @param y          The y-coordinate of the tile.
     * @param isWalkable Determines whether the tile is walkable or not.
     */
    public MapTile(int x, int y, TileType tileType, boolean isWalkable) {
        this.x = x;
        this.y = y;
        this.tileType = tileType;
        this.isWalkable = isWalkable;
        this.collisionBox = new Rectangle(x,y, TILE_SIZE, TILE_SIZE);
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

    public TileType getTileType() {
        return tileType;
    }
}
