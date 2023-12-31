package it.petrillo.jbomberman.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import it.petrillo.jbomberman.model.*;
import it.petrillo.jbomberman.model.characters.Bomberman;
import it.petrillo.jbomberman.model.gamemap.GameMap;
import it.petrillo.jbomberman.model.gamemap.MapTile;
import it.petrillo.jbomberman.model.objects.*;
import it.petrillo.jbomberman.util.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.petrillo.jbomberman.util.GameConstants.TILE_SIZE;

/**
 * The ObjectsManager class is responsible for managing and updating various game objects
 * and their interactions, such as bombs, power-ups, and explosions.
 */
public class ObjectsManager {

    private static ObjectsManager objectsManagerInstance;
    private final GameMap gameMap = GameMap.getInstance();
    private final Bomberman bomberman = Bomberman.getPlayerInstance();
    private final List<GameObject> objects = new ArrayList<>();
    private final List<GameObject> objectsToRemove = new ArrayList<>();
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
            PowerUp powerUp = GameEntityFactory.createPowerUp(x,y, PowerUpType.valueOf(type));
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

        for (GameObject obj : objects) {
            if (obj.isVisible()) {
                obj.update();
                if (obj instanceof Bomb bomb && ((Bomb) obj).isExploding()) {
                    if (!bomb.isExplosionStarted()) {
                        bomb.setExplosionStarted(true);
                        bomberman.alterBombReleased(-1);
                        initExplosionEffects(bomb);
                        bomb.setExplosionStarted(true);
                        AudioManager.getAudioManagerInstance().play("/SFX/explosion_sfx.wav",-12f);
                    }
                }
            }
        }
    }

    /**
     * Cleans up destroyed objects from the list of game objects.
     */
    private void cleanObjects() {
        List<GameObject> destroyedObjects = objects.stream()
                .filter(GameEntity::isToClean)
                .toList();

        for (GameObject obj : destroyedObjects) {
            if (obj instanceof PowerUp) {
                AudioManager.getAudioManagerInstance().play("/SFX/power_up_sfx.wav",-16f);
            }
            objectsToRemove.add(obj);
        }

        objects.removeAll(objectsToRemove);
        objectsToRemove.clear();
    }


    /**
     * Drops a bomb at the specified grid cell if there is space in Bomberman's backpack.
     *
     * @param x The x-coordinate of the grid cell.
     * @param y The y-coordinate of the grid cell.
     */
    public void dropBomb(int x, int y) {
        if (isCellEmpty(x,y) && bomberman.canDropBomb()) {
            objects.add((GameEntityFactory.createBomb(x, y)));
            bomberman.alterBombReleased(1);
            AudioManager.getAudioManagerInstance().play("/SFX/bomb_dropped_sfx.wav",-16f);
        }
    }

    /**
     * Initiates the explosion effects and interactions caused by a bomb.
     *
     * @param bomb The Bomb instance triggering the explosion.
     */
    public void initExplosionEffects(Bomb bomb) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int xIndex = bomb.getX() / TILE_SIZE;
        int yIndex = bomb.getY() / TILE_SIZE;
        ArrayList<Direction> explosionDirections = new ArrayList<>();

        for (int i = 0; i < Direction.values().length; i++) {
            Direction checkingDirection = null;
            switch (i) {
                case 0 -> checkingDirection = Direction.LEFT;
                case 1 -> checkingDirection = Direction.RIGHT;
                case 2 -> checkingDirection = Direction.UP;
                case 3 -> checkingDirection = Direction.DOWN;
            }
            int newX = xIndex + dx[i];
            int newY = yIndex + dy[i];
            if (gameMap.getTileFromCoords(newX,newY).getTileType() == MapTile.TileType.WALL)
                continue;
            else if (isCellEmpty(newX,newY)) {
                List<GameObject> objectList = getObjectsFromCoords(newX,newY);
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
            }
            Explosion explosion = GameEntityFactory.createExplosion(xIndex,yIndex,explosionDirections);
            explosionManager.addExplosion(explosion);
            collisionManager.addCollidable(explosion);
        }

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
     * Checks if a cell at the specified coordinates is empty or contains any GameObjects.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return true if the cell is empty, false if it contains a GameObject.
     */
    public boolean isCellEmpty(int x, int y) {
        Optional<GameObject> objectOptional = objects.stream()
                            .filter(obj -> obj.getX()/TILE_SIZE == x && obj.getY()/TILE_SIZE == y)
                            .findAny();
        return objectOptional.isEmpty();
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

}
