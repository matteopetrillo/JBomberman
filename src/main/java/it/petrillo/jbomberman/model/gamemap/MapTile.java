package it.petrillo.jbomberman.model.gamemap;



import java.awt.*;
import static it.petrillo.jbomberman.util.GameConstants.*;

/**
 * The MapTile class represents a tile within the game map grid. It encapsulates information about
 * the tile's position, type, walkability, and collision box.
 */
public class MapTile {

    private final Rectangle collisionBox;
    private final boolean isWalkable;
    private final TileType tileType;

    /**
     * Enumeration representing the different types of map tiles: WALL and FLOOR.
     */
    public enum TileType {
        WALL, FLOOR
    }

    /**
     * Creates a new MapTile instance with the specified position, tile type, and walkability status.
     *
     * @param x          The x-coordinate of the top-left corner of the tile.
     * @param y          The y-coordinate of the top-left corner of the tile.
     * @param tileType   The type of the tile, either WALL or FLOOR.
     * @param isWalkable Determines whether the tile is walkable or not.
     */
    public MapTile(int x, int y, TileType tileType, boolean isWalkable) {
        this.tileType = tileType;
        this.isWalkable = isWalkable;
        this.collisionBox = new Rectangle(x,y, TILE_SIZE, TILE_SIZE);
    }

    /**
     * Checks whether the tile is walkable.
     *
     * @return `true` if the tile is walkable, otherwise `false`.
     */
    public boolean isWalkable() {
        return isWalkable;
    }

    /**
     * Returns the type of the tile.
     *
     * @return The type of the tile, either WALL or FLOOR.
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * Returns the collision box of the tile.
     *
     * @return The collision box of the tile as a Rectangle.
     */
    public Rectangle getCollisionBox() {
        return collisionBox;
    }
}
