package it.petrillo.jbomberman.model;


import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapTile {
    private Rectangle collisionBox;
    private int x, y;
    private boolean bombPlaced, enemySpawn, playerSpawn, isWalkable, isDestroyable,softBlockPlaced;
    private BufferedImage img;

    protected MapTile(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.isWalkable = builder.isWalkable;
        this.isDestroyable = builder.isDestroyable;
        this.collisionBox = builder.collisionBox;
    }

    public void drawTile(Graphics2D g) {
        g.drawImage(img,x,y,Settings.TILE_SIZE,Settings.TILE_SIZE,null);
    }

    public boolean isSoftBlockPlaced() {
        return softBlockPlaced;
    }

    public void setSoftBlockPlaced(boolean softBlockPlaced) {
        this.softBlockPlaced = softBlockPlaced;
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

    public boolean isBombPlaced() {
        return bombPlaced;
    }

    public void setBombPlaced(boolean bombPlaced) {
        this.bombPlaced = bombPlaced;
    }

    public boolean isEnemySpawn() {
        return enemySpawn;
    }

    public void setEnemySpawn(boolean enemySpawn) {
        this.enemySpawn = enemySpawn;
    }

    public boolean isPlayerSpawn() {
        return playerSpawn;
    }

    public void setPlayerSpawn(boolean playerSpawn) {
        this.playerSpawn = playerSpawn;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public boolean isDestroyable() {
        return isDestroyable;
    }

    public void setDestroyable(boolean destroyable) {
        isDestroyable = destroyable;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }


    public static class Builder {
        private Rectangle collisionBox;
        private int x, y;
        private boolean bombPlaced, enemySpawn, playerSpawn, isWalkable, isDestroyable,softBlockPlaced;
        private BufferedImage img;
        public Builder(int x, int y, boolean isWalkable, boolean isDestroyable) {
            this.x = x;
            this.y = y;
            this.isWalkable = isWalkable;
            this.isDestroyable = isDestroyable;
            this.collisionBox = new Rectangle(x, y, Settings.TILE_SIZE, Settings.TILE_SIZE);
        }


        public Builder setBombPlaced(boolean bombPlaced) {
            this.bombPlaced = bombPlaced;
            return this;
        }

        public Builder setEnemySpawn(boolean enemySpawn) {
            this.enemySpawn = enemySpawn;
            return this;
        }

        public Builder setPlayerSpawn(boolean playerSpawn) {
            this.playerSpawn = playerSpawn;
            return this;
        }

        public Builder setSoftBlockPlaced(boolean softBlockPlaced) {
            this.softBlockPlaced = softBlockPlaced;
            return this;
        }

        public Builder setImage(BufferedImage img) {
            this.img = img;
            return this;
        }

        public MapTile build() {
            return new MapTile(this);
        }
    }
}
