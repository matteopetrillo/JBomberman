package it.petrillo.jbomberman.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameUtils {

    public static final int MAP_COLUMNS = 17;
    public static final int MAP_ROWS = 13;
    public static final int DEFAULT_TILE_SIZE = 16;
    public static final double SCALE = 4.0d;
    public static final int TILE_SIZE = (int) (DEFAULT_TILE_SIZE * SCALE);
    public static final int SCREEN_WIDTH = MAP_COLUMNS * TILE_SIZE;
    public static final int SCREEN_HEIGHT = MAP_ROWS * TILE_SIZE;


    public enum Direction {
        UP, DOWN, LEFT, RIGHT, IDLE
    }

    public enum NotificationType {
        DROP_BOMB
    }

    public enum SenderType {
        PLAYER, ENEMY_1, ENEMY_2, MAP
    }

    public static BufferedImage getImg(String path) {
        try (InputStream is = GameUtils.class.getResourceAsStream(path)) {
            if (is != null) {
                return ImageIO.read(is);
            }
        } catch (IOException e) {
            System.out.println("image not loaded");
            e.printStackTrace();
        }
        System.out.println("non ho trovato la risorsa");
        return null;
    }


    public static Map<String, JsonElement> getMultipleJsonFields(String jsonString, List<String> fields) {
        Map<String, JsonElement> selectedFields = new HashMap<>();
        try {
            InputStream inputStream = GameUtils.class.getResourceAsStream(jsonString);
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

    public static JsonObject getJsonObject(String jsonString, String field) {
        try {
            InputStream inputStream = GameUtils.class.getResourceAsStream(jsonString);
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

    public static JsonObject getJsonElement(String jsonString, String field) {
        try {
            InputStream inputStream = GameUtils.class.getResourceAsStream(jsonString);
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
