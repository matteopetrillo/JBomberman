package it.petrillo.jbomberman.model;

import java.awt.*;

public interface Collidable {
    Rectangle getCollisionBox();
    void onCollision(Collidable other);
}
