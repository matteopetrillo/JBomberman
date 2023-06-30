package it.petrillo.jbomberman.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class LevelSettings {
    @SerializedName("levelName")
    private String levelName;
    @SerializedName("mapWidth")
    private int mapWidth;
    @SerializedName("mapHeight")
    private int mapHeight;
    @SerializedName("enemies")
    private Map<EnemyType, Integer> enemies;


    public String getLevelName() {
        return levelName;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public Map<EnemyType, Integer> getEnemies() {
        return enemies;
    }

    @Override
    public String toString() {
        return "LevelSettings{ \n" +
                "levelName='" + levelName + '\'' +
                ",\n mapWidth=" + mapWidth +
                ",\n mapHeight=" + mapHeight + "\n" +
                enemies.toString() + "\n" +
                '}';
    }
}
