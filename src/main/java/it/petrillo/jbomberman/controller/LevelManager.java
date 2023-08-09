package it.petrillo.jbomberman.controller;

import com.google.gson.JsonElement;
import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.model.GameEntityFactory;
import it.petrillo.jbomberman.model.GameMap;

import java.util.List;
import java.util.Map;

import static it.petrillo.jbomberman.util.GameUtils.*;


public class LevelManager {

    private static LevelManager levelManagerInstance;
    private final Bomberman bomberman = Bomberman.getPlayerInstance();
    private final GameMap gameMap = GameMap.getInstance();
    private final ObjectsManager objectsManager = ObjectsManager.getInstance();
    private final EnemyManager enemyManager = EnemyManager.getInstance();
    private int currentLvl = 1;

    private LevelManager() {}
    public void loadLevel() {
        String settingPath = getJsonLvlPath(currentLvl);
        List<String> fields = List.of("sprite_sheets_path","player_spawn","soft_blocks_spawn","explosion","enemies_spawn","power_ups_spawn");
        Map<String,JsonElement> settings = getMultipleJsonFields(settingPath,fields);
        GameEntityFactory.setBombSpriteSheet(settings.get("sprite_sheets_path").getAsJsonObject().get("bomb").getAsString());
        GameEntityFactory.setSoftBlockSpriteSheet(settings.get("sprite_sheets_path").getAsJsonObject().get("soft_blocks").getAsString());
        GameEntityFactory.setExplosionSpriteSheet(settings.get("sprite_sheets_path").getAsJsonObject().get("explosion").getAsString());
        gameMap.initMap(settingPath);
        bomberman.setX(settings.get("player_spawn").getAsJsonObject().get("x").getAsInt()*TILE_SIZE);
        bomberman.setY(settings.get("player_spawn").getAsJsonObject().get("y").getAsInt()*TILE_SIZE);
        objectsManager.initObjects(settings.get("soft_blocks_spawn").getAsJsonArray(),settings.get("power_ups_spawn").getAsJsonArray());
        enemyManager.initEnemies(settings.get("enemies_spawn").getAsJsonArray());
    }


    public void loadNextLevel() {
        currentLvl++;
        gameMap.clear();
        objectsManager.clear();
        enemyManager.clear();
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
