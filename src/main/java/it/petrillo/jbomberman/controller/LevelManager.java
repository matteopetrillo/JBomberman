package it.petrillo.jbomberman.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.model.GameEntityFactory;
import it.petrillo.jbomberman.model.GameMap;
import it.petrillo.jbomberman.util.GameUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.petrillo.jbomberman.util.GameUtils.*;


public class LevelManager {

    private static LevelManager levelManagerInstance;
    private Bomberman bomberman = Bomberman.getPlayerInstance();
    private GameMap gameMap = GameMap.getInstance();
    private ObjectsManager objectsManager = ObjectsManager.getInstance();
    private EnemyManager enemyManager = EnemyManager.getInstance();
    private int currentLvl = 1;

    private LevelManager() {}
    public void loadLevel() {
        String settingPath = getJsonLvlPath(currentLvl);
        List<String> fields = List.of("sprite_sheets_path","player_spawn","soft_blocks_spawn","explosion","enemies_spawn");
        Map<String,JsonElement> settings = getMultipleJsonFields(settingPath,fields);
        GameEntityFactory.setBombSpriteSheet(settings.get("sprite_sheets_path").getAsJsonObject().get("bomb").getAsString());
        GameEntityFactory.setSoftBlockSpriteSheet(settings.get("sprite_sheets_path").getAsJsonObject().get("soft_blocks").getAsString());
        GameEntityFactory.setExplosionSpriteSheet(settings.get("sprite_sheets_path").getAsJsonObject().get("explosion").getAsString());
        gameMap.initMap(settingPath);
        bomberman.setX(settings.get("player_spawn").getAsJsonObject().get("x").getAsInt()*TILE_SIZE);
        bomberman.setY(settings.get("player_spawn").getAsJsonObject().get("y").getAsInt()*TILE_SIZE);
        objectsManager.initSoftBlocks(settings.get("soft_blocks_spawn").getAsJsonArray());
        enemyManager.initEnemies(settings.get("enemies_spawn").getAsJsonArray());

    }

    public void loadNextLevel() {
        currentLvl++;
        loadLevel();
    }


    private String getJsonLvlPath(int level) {
        return "/Level"+currentLvl+".json";
    }

    public static LevelManager getInstance() {
        if (levelManagerInstance == null)
            levelManagerInstance = new LevelManager();
        return levelManagerInstance;
    }

}
