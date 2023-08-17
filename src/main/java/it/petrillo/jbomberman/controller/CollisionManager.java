package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.*;
import it.petrillo.jbomberman.model.gamemap.GameMap;
import it.petrillo.jbomberman.model.interfaces.Collidable;
import it.petrillo.jbomberman.util.CollisionListener;
import it.petrillo.jbomberman.model.objects.GameObject;
import it.petrillo.jbomberman.util.Direction;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import static it.petrillo.jbomberman.util.GameConstants.*;

/**
 * Manages collision detection and interactions between collidable entities in the game world.
 */
public class CollisionManager implements CollisionListener {

    private static CollisionManager collisionManagerInstance;
    private GameMap gameMap;
    private ObjectsManager objectsManager;
    private final List<Collidable> collidables = new ArrayList<>();

    private CollisionManager() {}

    /**
     * Sets the reference to the game map for collision checking.
     *
     * @param gameMap The game map instance.
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Sets the reference to the objects manager for collision checking.
     *
     * @param objectsManager The objects manager instance.
     */
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

    /**
     * Checks whether the entity can move to a specific position without colliding with obstacles.
     *
     * @param dx           The change in x-coordinate.
     * @param dy           The change in y-coordinate.
     * @param collisionBox The collision box of the entity.
     * @return True if the entity can move to the specified position, false otherwise.
     */
    @Override
    public boolean canMoveThere(int dx, int dy, Area collisionBox) {
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        if (steps == 0) {
            return true;
        }

        Rectangle box = collisionBox.getBounds();
        int collisionX = (int) box.getX();
        int collisionY = (int) box.getY();
        int width = (int) box.getWidth();
        int height = (int) box.getHeight();

        for (int i = 0; i <= steps; i++) {
            int x = collisionX + (dx * i / steps);
            int y = collisionY + (dy * i / steps);

            if (!isWalkable(x, y) || !isWalkable(x + width, y) || !isWalkable(x + width, y + height) || !isWalkable(x, y + height)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Retrieves a list of available directions for movement based on the entity's collision box and speed.
     *
     * @param speed        The movement speed of the entity.
     * @param collisionBox The collision box of the entity.
     * @return A list of available directions for movement.
     */
    @Override
    public List<Direction> getAvailableDirections(int speed, Area collisionBox) {
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
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

            Area newCollisionBox = new Area(collisionBox);
            AffineTransform transform = AffineTransform.getTranslateInstance(dx, dy);
            newCollisionBox.transform(transform);

            if (canMoveThere(dx, dy, newCollisionBox))
                availableDirections.add(d);
        }

        return availableDirections;
    }

    /**
     * Checks for collisions between collidable entities and triggers collision events.
     */
    public void checkCollisions() {
        cleanCollidables();
        for (int i = 0; i < collidables.size()-1; i++) {
            for (int j = i+1; j < collidables.size(); j++) {
                Collidable g1 = collidables.get(i);
                Collidable g2 = collidables.get(j);
                Area g1CollisionBox = ((GameEntity)g1).getCollisionBox();
                Area g2CollisionBox = ((GameEntity)g2).getCollisionBox();

                if (g1CollisionBox != null && g2CollisionBox != null && checkAreasCollision(g1CollisionBox,g2CollisionBox)) {
                    g1.onCollision(g2);
                    g2.onCollision(g1);
                }
            }
        }
    }

    private boolean checkAreasCollision(Area area1, Area area2) {
        Area intersection = new Area(area1);
        intersection.intersect(area2);
        return !intersection.isEmpty();
    }

    /**
     * Registers a collidable entity for collision management.
     *
     * @param c The collidable entity to be registered.
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }


    /**
     * Removes collidable entities that are flagged for cleaning from the list of registered collidables.
     */
    private void cleanCollidables() {
        List<Collidable> toClean = new ArrayList<>();
        for (Collidable c : collidables) {
            if (c instanceof GameEntity) {
                if (((GameEntity) c).isToClean())
                    toClean.add(c);
            }
        }

        toClean.forEach(collidables::remove);
    }

    /**
     * Returns the singleton instance of the CollisionManager.
     *
     * @return The singleton instance of CollisionManager.
     */
    public static CollisionManager getInstance() {
        if (collisionManagerInstance == null)
            collisionManagerInstance = new CollisionManager();
        return collisionManagerInstance;
    }
}
