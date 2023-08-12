package it.petrillo.jbomberman.model;

import java.awt.*;
import java.util.List;

import static it.petrillo.jbomberman.util.GameSettings.*;

public interface CollisionListener {
    boolean canMoveThere(int dx, int dy, Rectangle collisionBox);
    List<Direction> getAvailableDirections(int speed, Rectangle collisionBox);
}
