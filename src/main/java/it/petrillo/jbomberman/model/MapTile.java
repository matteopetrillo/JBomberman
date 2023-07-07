package it.petrillo.jbomberman.model;


import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.Settings;

import java.awt.*;

public class MapTile {
    private boolean isWalkable;
    private Rectangle collisionBox;

    public MapTile(boolean isWalkable) {
        this.isWalkable = isWalkable;
        this.collisionBox = new Rectangle(Settings.TILE_SIZE, Settings.TILE_SIZE);

    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

}
