package it.petrillo.jbomberman.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import it.petrillo.jbomberman.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.petrillo.jbomberman.util.GameSettings.*;
import static it.petrillo.jbomberman.util.GameSettings.TILE_SIZE;

/**
 * The ObjectsManager class is responsible for managing and updating various game objects
 * and their interactions, such as bombs, power-ups, and explosions.
 */
public class ObjectsManager {

    private static ObjectsManager objectsManagerInstance;
    private final GameMap gameMap = GameMap.getInstance();
    private final Bomberman bomberman = Bomberman.getPlayerInstance();
    private final List<GameObject> objects = new ArrayList<>();
    private final ExplosionManager explosionManager = ExplosionManager.getInstance();
    private final CollisionManager collisionManager = CollisionManager.getInstance();
    private ObjectsManager() {}

    /**
     * Initializes game objects based on provided JSON data for soft blocks and power-ups.
     *
     * @param softBlocks The JSON array containing soft block data.
     * @param powerUps The JSON array containing power-up data.
     */
    public void initObjects(JsonArray softBlocks, JsonArray powerUps) {
        for (JsonElement element : powerUps) {
            int x = element.getAsJsonObject().get("x").getAsInt();
            int y = element.getAsJsonObject().get("y").getAsInt();
            String type = element.getAsJsonObject().get("type").getAsString();
            PowerUp powerUp = GameEntityFactory.createPowerUp(x,y,type);
            objects.add(powerUp);
            collisionManager.addCollidable(powerUp);
        }
        for (int i = 0; i < softBlocks.size(); i++) {
            for (int j = 0; j < softBlocks.get(i).getAsJsonArray().size(); j++) {
                int data = softBlocks.get(i).getAsJsonArray().get(j).getAsInt();
                if (data == 2)
                    objects.add(GameEntityFactory.createSoftBlock(j,i,true));
                else if (data == 3)
                    objects.add(GameEntityFactory.createSoftBlock(j,i,false));
            }
        }
    }

    /**
     * Updates the state of game objects, handles explosions, and cleans up destroyed objects.
     */
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

    /**
     * Drops a bomb at the specified grid cell if allowed by Bomberman.
     *
     * @param x The x-coordinate of the grid cell.
     * @param y The y-coordinate of the grid cell.
     */
    public void dropBomb(int x, int y) {
        List<GameObject> o = getObjectsFromCoords(x,y);
        if (o.isEmpty() && bomberman.canDropBomb()) {
            objects.add((GameEntityFactory.createBomb(x, y)));
            bomberman.alterBombReleased(1);
        }
    }

    /**
     * Handles the explosion caused by a bomb, affecting nearby objects and characters.
     *
     * @param bomb The Bomb instance triggering the explosion.
     */
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
            if (gameMap.getTileFromCoords(newX,newY).getTileType() == TileType.WALL)
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
                    if (c instanceof GameEntity) {
                        Rectangle collisionBox = ((GameEntity) c).getCollisionBox();
                        if (collisionBox.x / TILE_SIZE == newX && collisionBox.y / TILE_SIZE == newY && c instanceof GameCharacter)
                            ((GameCharacter) c).setHitted(true);
                    }
                }
            }
            Explosion explosion = GameEntityFactory.createExplosion(xIndex,yIndex,explosionDirections);
            explosionManager.addExplosion(explosion);
            collisionManager.addExplosionCollisions(explosion);
        }

        collisionManager.getCollidables().stream()
                .filter(c -> c instanceof GameCharacter && ((GameCharacter)c).isHitted())
                .forEach(c -> ((GameCharacter)c).hitCharacter());

    }

    /**
     * Cleans up destroyed objects from the list of game objects.
     */
    private void cleanObjects() {
        List<GameObject> destroyedObjects = objects.stream()
                .filter(GameEntity::isToClean)
                .collect(Collectors.toCollection(ArrayList::new));

        destroyedObjects.forEach(objects::remove);
    }

    /**
     * Retrieves a list of game objects located at a specific grid cell.
     *
     * @param x The x-coordinate of the grid cell.
     * @param y The y-coordinate of the grid cell.
     * @return A list of game objects at the specified grid cell.
     */
    public List<GameObject> getObjectsFromCoords(int x, int y) {
        return objects.stream()
                        .filter(obj -> obj.getX()/TILE_SIZE == x && obj.getY()/TILE_SIZE == y)
                        .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all game objects.
     *
     * @return A list of all game objects.
     */
    public List<GameObject> getObjects() {
        return objects;
    }

    /**
     * Retrieves the singleton instance of the ObjectsManager class.
     *
     * @return The singleton instance of ObjectsManager.
     */
    public static ObjectsManager getInstance() {
        if (objectsManagerInstance == null)
            objectsManagerInstance = new ObjectsManager();
        return objectsManagerInstance;
    }

    /**
     * Clears the list of game objects.
     */
    public void clear() {
        objects.clear();
    }
}
