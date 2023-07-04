package it.petrillo.jbomberman.model;

import java.awt.*;

public interface CollisionListener {
    boolean canMoveThere(int dx, int dy, Rectangle collisionBox);
}
