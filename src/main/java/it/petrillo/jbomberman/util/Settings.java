package it.petrillo.jbomberman.util;

import com.google.gson.annotations.SerializedName;
import it.petrillo.jbomberman.model.EnemyType;
import it.petrillo.jbomberman.model.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class Settings {
    /*
    Fields
     */
    @SerializedName("levelName")
    private String levelName;
    @SerializedName("enemies")
    private Map<EnemyType, Integer> enemies = new HashMap<>();
    @SerializedName("mapFilePath")
    private String mapFilePath;
    private int screenHeight;
    private int screenWidth;
    private int mapColumns=17;
    private int mapRows=13;
    public static final int DEFAULT_TILE_SIZE = 32;
    public static final double SCALE = 2.0d;
    public static final int TILE_SIZE = (int) (DEFAULT_TILE_SIZE * SCALE);


    /*
    Methods
     */
    public String getLevelName() {
        return levelName;
    }

    public int getScreenHeight() {
        return mapRows * TILE_SIZE;
    }

    public int getScreenWidth() {
        return mapColumns * TILE_SIZE;
    }

    public Map<EnemyType, Integer> getEnemies() {
        return enemies;
    }

    private void analyzeMap(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        mapRows = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (mapRows == 0) {
                mapColumns = line.length();
            }

            mapRows++;
        }

        scanner.close();
    }

    public String getMapFilePath() {
        return mapFilePath;
    }

    @Override
    public String toString() {
        return "Settings{ \n" +
                "levelName='" + levelName + '\'' +
                ",\n mapWidth=" + mapColumns +
                ",\n mapRows=" + mapRows + "\n" +
                enemies.toString() + "\n" +
                '}';
    }

}
