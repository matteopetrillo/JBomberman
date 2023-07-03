package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.CustomObservable;
import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.GameUtils.ObjectVisibility;
import it.petrillo.jbomberman.util.Position;

import java.awt.*;
public abstract class GameEntity extends CustomObservable implements Collidable{
    private Position position;
    private Rectangle collisionBox;
    private ObjectVisibility objectVisibility;
    public GameEntity(int x, int y, ObjectVisibility objectVisibility) {
        this.position = new Position(x,y);
        this.collisionBox = new Rectangle(position.getX(),
                position.getY(),
                GameUtils.Tile.SIZE.getValue(),
                GameUtils.Tile.SIZE.getValue());
        this.objectVisibility = objectVisibility;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
