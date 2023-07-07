package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.CustomObservable;
import it.petrillo.jbomberman.util.GameUtils.ObjectVisibility;
import it.petrillo.jbomberman.util.Settings;

import java.awt.*;

import static it.petrillo.jbomberman.util.GameUtils.*;

public abstract class GameEntity extends CustomObservable implements Collidable{
    protected Rectangle collisionBox;
    private ObjectVisibility objectVisibility;
    protected CollisionListener collisionListener;
    protected int x,y;
    protected Direction movingDirection;
    public GameEntity(int x, int y, ObjectVisibility objectVisibility ) {
        this.x = x;
        this.y = y;
        this.collisionBox = new Rectangle(x,y, Settings.TILE_SIZE, Settings.TILE_SIZE);
        this.objectVisibility = objectVisibility;
    }

    public void setCollisionListener(CollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public ObjectVisibility getObjectVisibility() {
        return objectVisibility;
    }

    public void setObjectVisibility(ObjectVisibility objectVisibility) {
        this.objectVisibility = objectVisibility;
    }
}
