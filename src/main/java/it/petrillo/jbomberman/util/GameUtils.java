package it.petrillo.jbomberman.util;


public class GameUtils {

    public enum SettingsPath {
        LEVEL_1("src/main/resources/level1_settings.json"),
        LEVEL_2("src/main/resources/level2_settings.json");

        private String value;
        SettingsPath(String value) {this.value = value;}
        public String getValue() {
            return value;
        }
    }


    public enum Direction {
        UP, DOWN, LEFT, RIGHT, IDLE
    }

}
