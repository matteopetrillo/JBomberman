package it.petrillo.jbomberman.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
public class GameSettings {

    public static final int MAP_COLUMNS = 17;
    public static final int MAP_ROWS = 13;
    public static final int DEFAULT_TILE_SIZE = 16;
    public static final double SCALE = 4.0d;
    public static final int TILE_SIZE = (int) (DEFAULT_TILE_SIZE * SCALE);
    public static final int SCREEN_WIDTH = MAP_COLUMNS * TILE_SIZE;
    public static final int SCREEN_HEIGHT = MAP_ROWS * TILE_SIZE;
    public static final String DB_PATH = "JBomberman/src/main/Database/PlayersDB.json";
    public static Font retroFont;

    static {
        try {
            InputStream is = GameSettings.class.getResourceAsStream("/RetroFont.ttf");
            retroFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public enum NotificationType {
        SCORE_UPDATE,HEALTH_UPDATE,BOMB_UPDATE,FINISH_LEVEL,GAME_OVER
    }

    public enum GameState {
        MENU,PLAYING,VICTORY,GAME_OVER,PAUSE,LOADING
    }

    public enum TileType {
        WALL, FLOOR
    }

    public static void drawBorder(Graphics2D g2d, int x, int y, int amount,String message) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(message, x - amount, y - amount);
        g2d.drawString(message, x + amount, y - amount);
        g2d.drawString(message, x - amount, y + amount);
        g2d.drawString(message, x + amount, y + amount);
    }

    /**
     * Retrieves an image resource from the provided path.
     *
     * @param path The path to the image resource.
     * @return The BufferedImage object representing the loaded image.
     */
    public static BufferedImage getImg(String path) {
        try (InputStream is = GameSettings.class.getResourceAsStream(path)) {
            if (is != null) {
                return ImageIO.read(is);
            }
        } catch (IOException e) {
            System.out.println("La risorsa al path: "+path+" non è stata caricata correttamente.");
            e.printStackTrace();
        }
        System.out.println("La risorsa al path: "+path+" non è stata trovata.");
        return null;
    }

    /**
     * Retrieves selected JSON fields from a JSON file as a map.
     *
     * @param jsonString The path to the JSON file.
     * @param fields     A list of JSON field names to retrieve.
     * @return A map containing selected JSON fields.
     */
    public static Map<String, JsonElement> getMultipleJsonFields(String jsonString, List<String> fields) {
        Map<String, JsonElement> selectedFields = new HashMap<>();
        try {
            InputStream inputStream = GameSettings.class.getResourceAsStream(jsonString);
            if (inputStream == null) {
                throw new IOException("Il file JSON non è stato trovato come risorsa: " + jsonString);
            }

            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = JsonParser.parseReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonObject();

            for (String field : fields) {
                if (jsonObject.has(field)) {
                    JsonElement jsonElement = jsonObject.get(field);
                    selectedFields.put(field, jsonElement);
                }
            }

            return selectedFields;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonObject getJsonObject(String jsonString) {
        try {
            InputStream inputStream = GameSettings.class.getResourceAsStream(jsonString);
            if (inputStream == null) {
                throw new IOException("Il file JSON non è stato trovato come risorsa: " + jsonString);
            }
            return JsonParser.parseReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonObject();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonObject getJsonElement(String jsonString, String field) {
        try {
            InputStream inputStream = GameSettings.class.getResourceAsStream(jsonString);
            if (inputStream == null) {
                throw new IOException("Il file JSON non è stato trovato come risorsa: " + jsonString);
            }

            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = JsonParser.parseReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonObject();

            return jsonObject.get(field).getAsJsonObject();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
