package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.GameMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class LevelManager {

    private static LevelManager levelManagerInstance;
    private int currentLvl = 1;
    private GameMap gameMap = GameMap.getInstance();

    private LevelManager() {}
    public void loadLevel() {
        String settingPath = getJsonLvlPath(currentLvl);
        gameMap.initMap(settingPath);
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
