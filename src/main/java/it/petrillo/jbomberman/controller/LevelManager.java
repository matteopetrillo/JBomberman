package it.petrillo.jbomberman.controller;

import com.google.gson.JsonElement;
import it.petrillo.jbomberman.model.*;
import it.petrillo.jbomberman.model.characters.Bomberman;
import it.petrillo.jbomberman.model.gamemap.GameMap;
import it.petrillo.jbomberman.util.GameStateListener;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static it.petrillo.jbomberman.util.GameConstants.*;
import static it.petrillo.jbomberman.util.UtilFunctions.getMultipleJsonFields;

/**
 * The LevelManager class handles loading and transitioning between game levels.
 */
public class LevelManager {

    private static LevelManager levelManagerInstance;
    private final Bomberman bomberman = Bomberman.getPlayerInstance();
    private final GameMap gameMap = GameMap.getInstance();
    private final ObjectsManager objectsManager = ObjectsManager.getInstance();
    private final EnemyManager enemyManager = EnemyManager.getInstance();
    private GameStateListener gameStateListener;
    private int currentLvl = 1;

    private LevelManager() {}

    /**
     * Loads the current level by reading level configuration from JSON files and initializing game objects.
     */
    public void loadLevel() {
        String settingPath = getJsonLvlPath();
        List<String> fields = List.of("mapImg","map_data","sprite_sheets_path", "player_spawn", "explosion", "enemies_spawn", "power_ups_spawn");
        Map<String, JsonElement> settings = getMultipleJsonFields(settingPath, fields);
        GameEntityFactory.setBombSpriteSheet(settings.get("sprite_sheets_path").getAsJsonObject().get("bomb").getAsString());
        GameEntityFactory.setSoftBlockSpriteSheet(settings.get("sprite_sheets_path").getAsJsonObject().get("soft_blocks").getAsString());
        GameEntityFactory.setExplosionSpriteSheet(settings.get("sprite_sheets_path").getAsJsonObject().get("explosion").getAsString());
        gameMap.initMap(settings.get("mapImg").getAsString(),settings.get("map_data").getAsJsonArray());
        int playerX = settings.get("player_spawn").getAsJsonObject().get("x").getAsInt() * TILE_SIZE;
        int playerY = settings.get("player_spawn").getAsJsonObject().get("y").getAsInt() * TILE_SIZE;
        bomberman.setPosition(playerX,playerY);
        objectsManager.initObjects(settings.get("map_data").getAsJsonArray(), settings.get("power_ups_spawn").getAsJsonArray());
        enemyManager.initEnemies(settings.get("enemies_spawn").getAsJsonArray());
    }

    /**
     * Loads the next level, clearing and resetting game elements.
     */
    public void loadNextLevel() {
        if (isLevelFinished() && !isGameFinished()) {
            currentLvl++;
            clearGame();
            loadLevel();
            gameStateListener.onLoading();
        }
        else if (isGameFinished() && isLevelFinished())
            gameStateListener.onWinning();
    }

    public void restartGame() {
        currentLvl = 1;
        clearGame();
        loadLevel();
    }

    private void clearGame() {
        gameMap.clearMap();
        objectsManager.getObjects().clear();
        enemyManager.getEnemies().clear();
        ExplosionManager.getInstance().getExplosionList().clear();
    }

    /**
     * Returns the JSON file path for the current level configuration.
     *
     * @return The JSON file path for the current level.
     */
    private String getJsonLvlPath() {
        return "/Level"+currentLvl+".json";
    }

    /**
     * Checks if the game is finished, i.e., there are no more levels to load.
     *
     * @return True if the game is finished, false otherwise.
     */
    public boolean isGameFinished() {
        String fileName = "Level"+(currentLvl+1)+".json";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        return inputStream == null;
    }

    /**
     * Checks if the current level is finished, i.e., all enemies are defeated.
     *
     * @return True if the level is finished, false otherwise.
     */
    public boolean isLevelFinished() {
        return enemyManager.getEnemies().isEmpty();
    }

    public void setGameStateListener(GameStateListener gameStateListener) {
        this.gameStateListener = gameStateListener;
    }

    public int getCurrentLvl() {
        return currentLvl;
    }

    /**
     * Returns the singleton instance of the LevelManager class.
     *
     * @return The singleton instance of LevelManager.
     */
    public static LevelManager getInstance() {
        if (levelManagerInstance == null)
            levelManagerInstance = new LevelManager();
        return levelManagerInstance;
    }

}
