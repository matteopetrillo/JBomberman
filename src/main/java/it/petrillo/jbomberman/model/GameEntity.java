package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.CustomObservable;
import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.Position;

import java.awt.*;
public abstract class GameEntity extends CustomObservable implements Collidable{
    Position position;
    Rectangle collisionBox;
    boolean isVisible;
    public GameEntity(int x, int y) {
        this.position = new Position(x,y);
        this.collisionBox = new Rectangle(position.getX(),
                position.getY(),
                GameUtils.Tile.SIZE.getValue(),
                GameUtils.Tile.SIZE.getValue());
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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}
