package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class CollisionManager implements CollisionListener {

    private static CollisionManager collisionManagerInstance;
    private GameMap gameMap;
    private ObjectsManager objectsManager = ObjectsManager.getInstance();
    private List<GameCharacter> characters = new ArrayList<>();

    private CollisionManager() {}
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;}

    private boolean isWalkable(int x, int y) {
        int xIndex = x / TILE_SIZE;
        int yIndex = y / TILE_SIZE;
        boolean tileWalkable = gameMap.getTileFromCoords(xIndex,yIndex).isWalkable();
        Optional<GameObject> objectOptional = objectsManager.getObjectFromCoords(xIndex,yIndex);
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
        for (int i = 0; i < characters.size()-1; i++) {
            for (int j = i+1; j < characters.size(); j++) {
                GameCharacter g1 = characters.get(i);
                GameCharacter g2 = characters.get(j);
                if (g1.getCollisionBox().intersects(g2.getCollisionBox())) {
                    g1.onCollision(g2);
                    g2.onCollision(g1);
                }
            }
        }
    }

    public List<GameCharacter> getCharacters() {
        return characters;
    }

    public void addCharacter(GameCharacter c) {
        characters.add(c);
    }

    public void removeCharacter(GameCharacter c) {
        characters.remove(c);
    }

    private void cleanObjects() {
        List<GameCharacter> destroyedObjects = characters.stream()
                .filter(e -> !e.isVisible())
                .collect(Collectors.toCollection(ArrayList::new));

        destroyedObjects.stream()
                .forEach(e -> characters.remove(e));
    }
    public static CollisionManager getInstance() {
        if (collisionManagerInstance == null)
            collisionManagerInstance = new CollisionManager();
        return collisionManagerInstance;
    }
}
