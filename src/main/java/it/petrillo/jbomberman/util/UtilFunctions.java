package it.petrillo.jbomberman.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * The UtilFunctions class provides utility methods for common tasks, such as reading JSON data,
 * loading images, and drawing borders on graphics contexts.
 */
public class UtilFunctions {

    /**
     * Retrieves selected fields from a JSON string and returns them as a map.
     *
     * @param jsonString The JSON string to extract data from.
     * @param fields The list of field names to retrieve from the JSON.
     * @return A map containing selected fields and their corresponding JSON elements.
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
     * Loads an image from the specified path.
     *
     * @param path The path to the image resource.
     * @return The loaded BufferedImage, or null if the image was not found.
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


    /**
     * Draws a border around a specified point with a given amount and message.
     *
     * @param g2d The Graphics2D context to draw on.
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param amount The border amount.
     * @param message The message to draw at the point.
     */
    public static void drawBorder(Graphics2D g2d, int x, int y, int amount, String message) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(message, x - amount, y - amount);
        g2d.drawString(message, x + amount, y - amount);
        g2d.drawString(message, x - amount, y + amount);
        g2d.drawString(message, x + amount, y + amount);
    }

}
