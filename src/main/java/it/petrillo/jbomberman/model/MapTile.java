package it.petrillo.jbomberman.model;


import it.petrillo.jbomberman.util.GameUtils;

import java.awt.*;

public class MapTile {
    private boolean isWalkable;
    private Rectangle collisionBox;

    public MapTile(boolean isWalkable) {
        this.isWalkable = isWalkable;
        this.collisionBox = new Rectangle(GameUtils.Tile.SIZE.getValue(), GameUtils.Tile.SIZE.getValue());

    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

}
