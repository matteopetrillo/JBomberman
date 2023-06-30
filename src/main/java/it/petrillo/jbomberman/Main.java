package it.petrillo.jbomberman;

import it.petrillo.jbomberman.model.LevelSettings;
import it.petrillo.jbomberman.util.JsonLoader;

public class Main {
    public static void main(String[] args) {
        String filepath = "src/main/resources/level1_settings.json";
        LevelSettings levelSettings = JsonLoader.loadJson(filepath, LevelSettings.class);
        System.out.println(levelSettings.getEnemies());
    }
}
