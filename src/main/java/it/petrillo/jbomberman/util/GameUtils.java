package it.petrillo.jbomberman.util;

public enum GameUtils {
    TILE_SIZE(32);

    private int value;
    GameUtils(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
