package it.petrillo.jbomberman.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.model.GameMap;
import it.petrillo.jbomberman.util.GameUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static it.petrillo.jbomberman.util.GameUtils.*;


public class LevelManager {

    private static LevelManager levelManagerInstance;
    private Bomberman bomberman = Bomberman.getPlayerInstance();
    private GameMap gameMap = GameMap.getInstance();
    private int currentLvl = 1;

    private LevelManager() {}
    public void loadLevel() {
        String settingPath = getJsonLvlPath(currentLvl);
        gameMap.initMap(settingPath);
        JsonObject playerSpawn = getJsonField(settingPath,"player_spawn");
        bomberman.setX(playerSpawn.get("x").getAsInt());
        bomberman.setY(playerSpawn.get("y").getAsInt());
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
