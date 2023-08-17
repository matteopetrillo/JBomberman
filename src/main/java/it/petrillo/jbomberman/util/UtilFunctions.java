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

public class UtilFunctions {

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
            InputStream inputStream = GameConstants.class.getResourceAsStream(jsonString);
            if (inputStream == null) {
                throw new IOException("Il file JSON non è stato trovato come risorsa: " + jsonString);
            }

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


    /**
     * Retrieves an image resource from the provided path.
     *
     * @param path The path to the image resource.
     * @return The BufferedImage object representing the loaded image.
     */
    public static BufferedImage getImg(String path) {
        try (InputStream is = GameConstants.class.getResourceAsStream(path)) {
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

    public static void drawBorder(Graphics2D g2d, int x, int y, int amount, String message) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(message, x - amount, y - amount);
        g2d.drawString(message, x + amount, y - amount);
        g2d.drawString(message, x - amount, y + amount);
        g2d.drawString(message, x + amount, y + amount);
    }
}
