package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.CollisionListener;
import it.petrillo.jbomberman.model.GameMap;
import it.petrillo.jbomberman.model.GameObject;

import java.awt.*;
import java.util.Optional;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class CollisionManager implements CollisionListener {

    private static CollisionManager collisionManagerInstance;
    private static GameMap gameMap;

    private CollisionManager() {}
    public void setGameMap(GameMap gameMap) {
        CollisionManager.gameMap = gameMap;}

    private static boolean isWalkable(int x, int y) {
        int xIndex = x / TILE_SIZE;
        int yIndex = y / TILE_SIZE;
        boolean tileWalkable = gameMap.getTileFromCoords(xIndex,yIndex).isWalkable();
        Optional<GameObject> objectOptional = gameMap.getObjectFromCoords(xIndex,yIndex);
        if (objectOptional.isPresent()) {
            GameObject object = objectOptional.get();
            return tileWalkable && !object.isSolid();
        }
        return tileWalkable;
    }

    @Override
    public boolean canMoveThere(int dx, int dy, Rectangle collisionBox) {
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        double xStep = (double) dx / steps;
        double yStep = (double) dy / steps;

        for (int i = 0; i < steps; i++) {
            int x = (int) (collisionBox.getX() + i * xStep);
            int y = (int) (collisionBox.getY() + i * yStep);
            int width = (int) collisionBox.getWidth();
            int height = (int) collisionBox.getHeight();

            if (!isWalkable(x, y) || !isWalkable(x + width, y) || !isWalkable(x + width, y + height) || !isWalkable(x, y + height)) {
                return false;
            }
        }

        return true;
    }


    public static CollisionManager getInstance() {
        if (collisionManagerInstance == null)
            collisionManagerInstance = new CollisionManager();
        return collisionManagerInstance;
    }
}
