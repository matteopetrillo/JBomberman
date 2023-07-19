package it.petrillo.jbomberman.model;


import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.Settings;

import java.awt.*;

public class MapTile {
    private boolean isWalkable;
    private boolean isDestroyable;
    private Rectangle collisionBox;
    private int tileID, x, y;

    public MapTile(boolean isWalkable, boolean isDestroyable, int tileID, int x, int y) {
        this.isWalkable = isWalkable;
        this.isDestroyable = isDestroyable;
        this.tileID = tileID;
        this.x = x;
        this.y = y;
        this.collisionBox = new Rectangle(x,y,Settings.TILE_SIZE, Settings.TILE_SIZE);

    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public boolean isDestroyable() {
        return isDestroyable;
    }

    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public void setDestroyable(boolean destroyable) {
        isDestroyable = destroyable;
    }

    @Override
    public String toString() {
        return "MapTile{" +
                "isWalkable=" + isWalkable +
                ", isDestroyable=" + isDestroyable +
                ", tileID=" + tileID +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
