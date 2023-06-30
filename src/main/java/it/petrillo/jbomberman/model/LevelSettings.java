package it.petrillo.jbomberman.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class LevelSettings {
    @SerializedName("levelName")
    private String levelName;
    @SerializedName("mapColumns")
    private int mapColumns;
    @SerializedName("mapRows")
    private int mapRows;
    @SerializedName("enemies")
    private Map<EnemyType, Integer> enemies;


    public String getLevelName() {
        return levelName;
    }

    public int getMapColumns() {
        return mapColumns;
    }

    public int getMapRows() {
        return mapRows;
    }

    public Map<EnemyType, Integer> getEnemies() {
        return enemies;
    }

    @Override
    public String toString() {
        return "LevelSettings{ \n" +
                "levelName='" + levelName + '\'' +
                ",\n mapWidth=" + mapColumns +
                ",\n mapRows=" + mapRows + "\n" +
                enemies.toString() + "\n" +
                '}';
    }
}
