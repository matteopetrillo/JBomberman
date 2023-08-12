package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.CustomObservable;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameSettings.*;

public abstract class GameEntity extends CustomObservable implements Animatable {
    protected Rectangle collisionBox;
    protected boolean visible, toClean;
    protected int x,y,animationTick,animationIndex, animationSpeed, xCollisionOffset, yCollisionOffset;
    protected double entityScale = 1.0d;
    protected BufferedImage spriteSheet;
    protected BufferedImage[][] spriteAnimation;
    public GameEntity(int x, int y) {
        this.x = x;
        this.y = y;
        this.collisionBox = new Rectangle(x,y, TILE_SIZE, TILE_SIZE);
    }


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

    public boolean isToClean() {
        return toClean;
    }

    public void setToClean(boolean toClean) {
        this.toClean = toClean;
    }
}
