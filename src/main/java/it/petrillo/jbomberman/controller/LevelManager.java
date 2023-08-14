package it.petrillo.jbomberman.controller;

import com.google.gson.JsonElement;
import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.model.GameEntityFactory;
import it.petrillo.jbomberman.model.GameMap;
import it.petrillo.jbomberman.model.GameStateListener;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static it.petrillo.jbomberman.util.GameSettings.*;

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
        bomberman.setX(settings.get("player_spawn").getAsJsonObject().get("x").getAsInt() * TILE_SIZE);
        bomberman.setY(settings.get("player_spawn").getAsJsonObject().get("y").getAsInt() * TILE_SIZE);
        objectsManager.initObjects(settings.get("map_data").getAsJsonArray(), settings.get("power_ups_spawn").getAsJsonArray());
        enemyManager.initEnemies(settings.get("enemies_spawn").getAsJsonArray());
    }

    /**
     * Loads the next level, clearing and resetting game elements.
     */
    public void loadNextLevel() {
        if (isLevelFinished() && !isGameFinished()) {
            gameStateListener.onLoading();
            currentLvl++;
            gameMap.clear();
            objectsManager.clear();
            enemyManager.clear();
            loadLevel();
            Timer loadingTimer = new Timer(4000, e -> {
                gameStateListener.onPlaying();
            });
            loadingTimer.setRepeats(false);
            loadingTimer.start();
        }
        else if (isGameFinished())
            gameStateListener.onWinning();
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
        if (inputStream != null)
            return false;
        else
            return true;
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
