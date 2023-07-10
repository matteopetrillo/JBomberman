package it.petrillo.jbomberman.model;


import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.Settings;

import java.awt.*;

public class MapTile {
    private boolean isWalkable;
    private Rectangle collisionBox;
    private int tileID, x, y;

    public MapTile(boolean isWalkable, int tileID, int x, int y) {
        this.isWalkable = isWalkable;
        this.tileID = tileID;
        this.x = x;
        this.y = y;
        this.collisionBox = new Rectangle(x,y,Settings.TILE_SIZE, Settings.TILE_SIZE);

    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public int getTileID() {
        return tileID;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

}
