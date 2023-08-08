package it.petrillo.jbomberman.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import it.petrillo.jbomberman.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.petrillo.jbomberman.util.GameUtils.*;
import static it.petrillo.jbomberman.util.GameUtils.TILE_SIZE;

public class ObjectsManager {

    private static ObjectsManager objectsManagerInstance;
    private GameMap gameMap = GameMap.getInstance();
    private Bomberman bomberman = Bomberman.getPlayerInstance();
    private CollisionManager collisionManager;
    private List<GameObject> objects = new ArrayList<>();

    private ObjectsManager() {}

    public void initSoftBlocks(JsonArray jsonArray) {
        for (JsonElement element : jsonArray) {
            int x = element.getAsJsonObject().get("x").getAsInt();
            int y = element.getAsJsonObject().get("y").getAsInt();
            SoftBlock softBlock = GameEntityFactory.createSoftBlock(x, y);
            if (y-1 >=0) {
                MapTile upperTile = gameMap.getTileFromCoords(x,y-1);
                if (upperTile.getTileId() == 9)
                    softBlock.setHasShadow(true);
            }
            objects.add(softBlock);
        }

        collisionManager = CollisionManager.getInstance();
    }
    public void updateObjects() {
        cleanObjects();
        for (GameObject e : objects) {
            if (e.isVisible()) {
                e.update();
                if (e instanceof Bomb && e.isExploding()) {
                    Bomb bomb = (Bomb) e;
                    if (!bomb.isExplosionStarted()) {
                        handleExplosion(bomb);
                        bomb.setExplosionStarted(true);
                    }
                }
            }
        }
    }

    public void dropBomb(int x, int y) {
        Optional<GameObject> o = getObjectFromCoords(x,y);
        if (o.isEmpty())
            objects.add((GameEntityFactory.createBomb(x,y)));
    }

    public void handleExplosion(Bomb bomb) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int xIndex = bomb.getX() / TILE_SIZE;
        int yIndex = bomb.getY() / TILE_SIZE;
        ArrayList<Direction> explosionDirections = new ArrayList<>();
        if (bomberman.getCollisionBox().x / TILE_SIZE == xIndex && bomberman.getCollisionBox().y / TILE_SIZE == yIndex)
            bomberman.setHitted(true);

        for (GameCharacter character : collisionManager.getCharacters()) {
            Rectangle collisionBox = character.getCollisionBox();
            if (collisionBox.x / TILE_SIZE == xIndex && collisionBox.y / TILE_SIZE == yIndex)
                character.setHitted(true);
        }

        for (int i = 0; i < 4; i++) {
            Direction checkingDirection = null;
            switch (i) {
                case 0 -> checkingDirection = Direction.LEFT;
                case 1 -> checkingDirection = Direction.RIGHT;
                case 2 -> checkingDirection = Direction.UP;
                case 3 -> checkingDirection = Direction.DOWN;
            }
            int newX = xIndex + dx[i];
            int newY = yIndex + dy[i];
            Optional<GameObject> ipoteticObject = getObjectFromCoords(newX,newY);
            if (!gameMap.getTileFromCoords(newX,newY).isWalkable())
                continue;
            else if (ipoteticObject.isPresent()) {
                GameObject gameObject = ipoteticObject.get();
                if (gameObject instanceof SoftBlock)
                    ((SoftBlock) gameObject).setExploding(true);
            }
            else {
                explosionDirections.add(checkingDirection);
                for (GameCharacter character : collisionManager.getCharacters()) {
                    Rectangle collisionBox = character.getCollisionBox();
                    if (collisionBox.x / TILE_SIZE == newX && collisionBox.y / TILE_SIZE == newY)
                        character.setHitted(true);
                }
            }
            ExplosionManager.addExplosion(GameEntityFactory.createExplosion(xIndex,yIndex,explosionDirections));
        }

        collisionManager.getCharacters().stream()
                .filter(c -> c.isHitted())
                .forEach(c -> c.hitCharacter());

    }

    private void cleanObjects() {
        List<GameObject> destroyedObjects = objects.stream()
                .filter(e -> !e.isVisible())
                .collect(Collectors.toCollection(ArrayList::new));

        destroyedObjects.stream()
                .forEach(e -> objects.remove(e));
    }

    public Optional<GameObject> getObjectFromCoords(int x, int y) {
        return objects.stream()
                .filter(obj -> obj.getX()/TILE_SIZE == x && obj.getY()/TILE_SIZE == y)
                .findAny();
    }
    public List<GameObject> getObjects() {
        return objects;
    }

    public static ObjectsManager getInstance() {
        if (objectsManagerInstance == null)
            objectsManagerInstance = new ObjectsManager();
        return objectsManagerInstance;
    }
}
