package it.petrillo.jbomberman.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import it.petrillo.jbomberman.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.petrillo.jbomberman.util.GameUtils.*;
import static it.petrillo.jbomberman.util.GameUtils.TILE_SIZE;

public class ObjectsManager {

    private static ObjectsManager objectsManagerInstance;
    private final GameMap gameMap = GameMap.getInstance();
    private final Bomberman bomberman = Bomberman.getPlayerInstance();
    private final List<GameObject> objects = new ArrayList<>();
    private final ExplosionManager explosionManager = ExplosionManager.getInstance();
    private final CollisionManager collisionManager = CollisionManager.getInstance();
    private ObjectsManager() {}

    public void initObjects(JsonArray softBlocks, JsonArray powerUps) {
        for (JsonElement element : powerUps) {
            int x = element.getAsJsonObject().get("x").getAsInt();
            int y = element.getAsJsonObject().get("y").getAsInt();
            String type = element.getAsJsonObject().get("type").getAsString();
            PowerUp powerUp = GameEntityFactory.createPowerUp(x,y,type);
            objects.add(powerUp);
            collisionManager.addCollidable(powerUp);
        }
        for (JsonElement element : softBlocks) {
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
    }

    public void updateObjects() {
        cleanObjects();
        for (GameObject e : objects) {
            if (e.isVisible()) {
                e.update();
                if (e instanceof Bomb bomb && ((Bomb) e).isExploding()) {
                    if (!bomb.isExplosionStarted()) {
                        bomb.setExplosionStarted(true);
                        bomberman.alterBombReleased(-1);
                        handleExplosion(bomb);
                        bomb.setExplosionStarted(true);
                    }
                }
            }
        }
    }

    public void dropBomb(int x, int y) {
        List<GameObject> o = getObjectsFromCoords(x,y);
        if (o.isEmpty() && bomberman.canDropBomb()) {
            objects.add((GameEntityFactory.createBomb(x, y)));
            bomberman.alterBombReleased(1);
        }
    }

    public void handleExplosion(Bomb bomb) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int xIndex = bomb.getX() / TILE_SIZE;
        int yIndex = bomb.getY() / TILE_SIZE;
        ArrayList<Direction> explosionDirections = new ArrayList<>();
        if (bomberman.getCollisionBox().x / TILE_SIZE == xIndex && bomberman.getCollisionBox().y / TILE_SIZE == yIndex)
            bomberman.setHitted(true);

        for (Collidable c : collisionManager.getCollidables()) {
            Rectangle collisionBox = ((GameEntity)c).getCollisionBox();
            if (collisionBox.x / TILE_SIZE == xIndex && collisionBox.y / TILE_SIZE == yIndex && c instanceof GameCharacter)
                ((GameCharacter)c).setHitted(true);
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
            List<GameObject> objectList = getObjectsFromCoords(newX,newY);
            if (!gameMap.getTileFromCoords(newX,newY).isWalkable())
                continue;
            else if (!objectList.isEmpty()) {
                for (GameObject obj : objectList) {
                    if (obj instanceof SoftBlock)
                        ((SoftBlock) obj).setExploding(true);
                    else if (obj instanceof PowerUp) {
                        obj.setVisible(true);
                    }
                }
            }
            else {
                explosionDirections.add(checkingDirection);
                for (Collidable c : collisionManager.getCollidables()) {
                    Rectangle collisionBox = ((GameEntity)c).getCollisionBox();
                    if (collisionBox.x / TILE_SIZE == newX && collisionBox.y / TILE_SIZE == newY && c instanceof GameCharacter)
                        ((GameCharacter)c).setHitted(true);
                }
            }
            explosionManager.addExplosion(GameEntityFactory.createExplosion(xIndex,yIndex,explosionDirections));
        }

        collisionManager.getCollidables().stream()
                .filter(c -> c instanceof GameCharacter && ((GameCharacter)c).isHitted())
                .forEach(c -> ((GameCharacter)c).hitCharacter());

    }

    private void cleanObjects() {
        List<GameObject> destroyedObjects = objects.stream()
                .filter(GameEntity::isToClean)
                .collect(Collectors.toCollection(ArrayList::new));

        destroyedObjects.forEach(objects::remove);
    }

    public List<GameObject> getObjectsFromCoords(int x, int y) {
        return objects.stream()
                        .filter(obj -> obj.getX()/TILE_SIZE == x && obj.getY()/TILE_SIZE == y)
                        .collect(Collectors.toList());
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
