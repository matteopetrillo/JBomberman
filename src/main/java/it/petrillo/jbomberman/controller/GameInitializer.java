package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.LevelSettings;
import it.petrillo.jbomberman.util.JsonLoader;

public class GameInitializer {
    GameManager gameManager;
    public void initGame() {
        String filepath = "src/main/resources/level1_settings.json";
        LevelSettings levelSettings = JsonLoader.loadJson(filepath, LevelSettings.class);
        gameManager = new GameManager(levelSettings.getMapColumns(), levelSettings.getMapRows());
    }

    public void startGame() {
        gameManager.openFrame();
    }
}
