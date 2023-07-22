package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.CustomObservable;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameUtils.*;

public abstract class GameEntity extends CustomObservable {
    protected Rectangle collisionBox;
    private boolean visible;
    protected int x,y,animationTick,animationIndex, animationSpeed, xCollisionOffset, yCollisionOffset, xSpeed, ySpeed, entitySpeed;
    protected double entityScale = 1.0d;
    protected BufferedImage spriteSheet;
    protected BufferedImage[][] spriteAnimation;
    public GameEntity(int x, int y, boolean visible) {
        this.x = x;
        this.y = y;
        this.collisionBox = new Rectangle(x,y, TILE_SIZE, TILE_SIZE);
        this.visible = visible;
    }

    public abstract void draw(Graphics2D g);
    protected abstract void loadSprites(String path);

    public abstract void updateStatus();

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


}
