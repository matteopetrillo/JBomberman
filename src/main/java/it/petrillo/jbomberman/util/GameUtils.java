package it.petrillo.jbomberman.util;


public class GameUtils {

    public enum LevelSettings {
        LEVEL_1("src/main/resources/level1_settings.json");
        private String value;
        LevelSettings(String value) {this.value = value;}
        public String getValue() {
            return value;
        }
    }
    public enum Tile {
        SIZE(32);
        private int value;
        Tile(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ObjectVisibility {
        VISIBLE, NOT_VISIBLE
    }

}
