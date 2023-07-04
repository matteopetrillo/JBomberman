package it.petrillo.jbomberman.util;

import com.google.gson.annotations.SerializedName;
import it.petrillo.jbomberman.model.EnemyType;

import java.io.File;
import java.io.FileNotFoundException;
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
    private Map<EnemyType, Integer> enemies;
    @SerializedName("mapFilePath")
    private String mapFilePath;
    private int screenHeight;
    private int screenWidth;
    private int mapColumns;
    private int mapRows;


    /*
    Methods
     */
    public String getLevelName() {
        return levelName;
    }
    public int getMapColumns() {
        try {
            analyzeMap(mapFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mapColumns;
    }
    public int getMapRows() {
        try {
            analyzeMap(mapFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mapRows;
    }

    public int getScreenHeight() {
        return getMapRows()* Tile.SIZE.getValue();
    }

    public int getScreenWidth() {
        return getMapColumns()* Tile.SIZE.getValue();
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
        System.out.println("rows: "+mapRows+" col: "+mapColumns);
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
