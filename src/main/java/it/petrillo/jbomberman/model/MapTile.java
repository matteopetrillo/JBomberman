package it.petrillo.jbomberman.model;



import java.awt.*;
import static it.petrillo.jbomberman.util.GameSettings.*;

public class MapTile {
    private Rectangle collisionBox;
    private int x, y, tileId;
    private boolean isWalkable;

    public MapTile(int x, int y, int tileId, boolean isWalkable) {
        this.x = x;
        this.y = y;
        this.tileId = tileId;
        this.isWalkable = isWalkable;
        collisionBox = new Rectangle(x,y, TILE_SIZE, TILE_SIZE);
    }

    public int getTileId() {
        return tileId;
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }
}
