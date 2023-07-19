package it.petrillo.jbomberman.util;


public class GameUtils {

    public enum SettingsPath {
        LEVEL_1("C:\\Coding\\Java\\JBomberman\\JBomberman\\src\\main\\resources\\level1_settings.json");

        private String value;
        SettingsPath(String value) {this.value = value;}
        public String getValue() {
            return value;
        }
    }


    public enum Direction {
        UP, DOWN, LEFT, RIGHT, IDLE
    }

    public enum NotificationType {
        DROP_BOMB
    }

}
