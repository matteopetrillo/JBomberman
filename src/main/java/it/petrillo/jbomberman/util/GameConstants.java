package it.petrillo.jbomberman.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static final Font RETRO_FONT;
    public static final String USER_BASE_DIR = System.getProperty("user.dir");

    static {
        try {
            InputStream is = GameConstants.class.getResourceAsStream("/RetroFont.ttf");
            RETRO_FONT = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
