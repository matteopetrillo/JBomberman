package it.petrillo.jbomberman.model.gamemap;



import java.awt.*;
import static it.petrillo.jbomberman.util.GameConstants.*;

/**
 * The MapTile class represents a tile within the game map grid. It contains information about the tile's
 * position, ID, walkability, and collision box.
 */
public class MapTile {

    private final Rectangle collisionBox;
    private final boolean isWalkable;
    private final TileType tileType;

    public enum TileType {
        WALL, FLOOR
    }



    /**
     * Creates a new MapTile instance with the specified position, tile ID, and walkability status.
     *
     * @param x          The x-coordinate of the tile.
     * @param y          The y-coordinate of the tile.
     * @param isWalkable Determines whether the tile is walkable or not.
     */
    public MapTile(int x, int y, TileType tileType, boolean isWalkable) {
        this.tileType = tileType;
        this.isWalkable = isWalkable;
        this.collisionBox = new Rectangle(x,y, TILE_SIZE, TILE_SIZE);
    }

    /**
     * Returns whether the tile is walkable.
     *
     * @return `true` if the tile is walkable, otherwise `false`.
     */
    public boolean isWalkable() {
        return isWalkable;
    }

    public TileType getTileType() {
        return tileType;
    }
}
