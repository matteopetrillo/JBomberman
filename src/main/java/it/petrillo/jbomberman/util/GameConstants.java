package it.petrillo.jbomberman.util;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class holds various game settings and utility methods for managing resources and JSON data.
 */
public class GameConstants {

    public static final int MAP_COLUMNS = 17;
    public static final int MAP_ROWS = 13;
    public static final int DEFAULT_TILE_SIZE = 16;
    public static final double SCALE = 4.0d;
    public static final int TILE_SIZE = (int) (DEFAULT_TILE_SIZE * SCALE);
    public static final int SCREEN_WIDTH = MAP_COLUMNS * TILE_SIZE;
    public static final int SCREEN_HEIGHT = MAP_ROWS * TILE_SIZE;
    public static final String DB_PATH = "/src/main/Database/PlayersDB.json";
    public static Font RETRO_FONT = null;
    public static final String USER_BASE_DIR = System.getProperty("user.dir");

    static {
        try {
            InputStream is = GameConstants.class.getResourceAsStream("/RetroFont.ttf");
            if (is != null) {
                RETRO_FONT = Font.createFont(Font.TRUETYPE_FONT, is);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
