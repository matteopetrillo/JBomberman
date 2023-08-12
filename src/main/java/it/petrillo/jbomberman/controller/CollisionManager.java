package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.petrillo.jbomberman.util.GameSettings.*;

public class CollisionManager implements CollisionListener {

    private static CollisionManager collisionManagerInstance;
    private GameMap gameMap;
    private ObjectsManager objectsManager;
    private final List<Collidable> collidables = new ArrayList<>();

    private CollisionManager() {}
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void setObjectsManager(ObjectsManager objectsManager) {
        this.objectsManager = objectsManager;
    }

    private boolean isWalkable(int x, int y) {
        int xIndex = x / TILE_SIZE;
        int yIndex = y / TILE_SIZE;
        boolean tileWalkable = gameMap.getTileFromCoords(xIndex,yIndex).isWalkable();
        List<GameObject> objects = objectsManager.getObjectsFromCoords(xIndex,yIndex);
        if (!objects.isEmpty()) {
            boolean solid = true;
            for (GameObject obj : objects) {
                solid = obj.isSolid();
            }
            return tileWalkable && !solid;
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

            if (!isWalkable(x, y) || !isWalkable(x + width, y) || !isWalkable(x + width, y + height) || !isWalkable(x, y + height))
                return false;
        }

        return true;
    }

    @Override
    public List<Direction> getAvailableDirections(int speed, Rectangle collisionBox) {
        Direction[] directions = {Direction.UP,Direction.DOWN,Direction.LEFT,Direction.RIGHT};
        List<Direction> availableDirections = new ArrayList<>();

        for (Direction d : directions) {
            int dx = 0;
            int dy = 0;
            if (d == Direction.UP)
                dy = -speed;
            else if (d == Direction.DOWN)
                dy = speed;
            else if (d == Direction.LEFT)
                dx = -speed;
            else if (d == Direction.RIGHT)
                dx = speed;

            if (canMoveThere(dx,dy,new Rectangle(collisionBox)))
                availableDirections.add(d);

        }

        return availableDirections;
    }

    public void checkCollisions() {
        cleanObjects();
        for (int i = 0; i < collidables.size()-1; i++) {
            for (int j = i+1; j < collidables.size(); j++) {
                Collidable g1 = collidables.get(i);
                Collidable g2 = collidables.get(j);
                Rectangle g1CollisionBox = ((GameEntity)g1).getCollisionBox();
                Rectangle g2CollisionBox = ((GameEntity)g2).getCollisionBox();
                if (g1CollisionBox.intersects(g2CollisionBox)) {
                    g1.onCollision(g2);
                    g2.onCollision(g1);
                }
            }
        }
    }

    public List<Collidable> getCollidables() {
        return collidables;
    }

    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    private void cleanObjects() {
        List<Collidable> toClean = collidables.stream()
                .filter(e -> ((GameEntity)e).isToClean())
                .collect(Collectors.toCollection(ArrayList::new));

        toClean.forEach(collidables::remove);
    }
    public static CollisionManager getInstance() {
        if (collisionManagerInstance == null)
            collisionManagerInstance = new CollisionManager();
        return collisionManagerInstance;
    }
}
