package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.GameUtils;

import java.awt.*;
import java.util.List;

import static it.petrillo.jbomberman.util.GameUtils.*;

public interface CollisionListener {
    boolean canMoveThere(int dx, int dy, Rectangle collisionBox);
    List<Direction> getAvailableDirections(int speed, Rectangle collisionBox);
}
