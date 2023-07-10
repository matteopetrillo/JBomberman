package it.petrillo.jbomberman.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GameMap {

    private MapTile[][] mapTiles;

    public GameMap(String filePath) {
        try {
            loadMapFromJson(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadMapFromJson(String filePath) throws FileNotFoundException {

        try (FileReader fileReader = new FileReader(filePath)) {
            // Parsa il JSON utilizzando Gson
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(fileReader).getAsJsonObject();
            JsonArray layersArray = jsonObject.getAsJsonArray("layers");

            // Verifica se l'array "layers" ha elementi
            if (layersArray.size() > 0) {
                JsonObject firstLayer = layersArray.get(0).getAsJsonObject();
                String name = firstLayer.get("name").getAsString();
                if (name.equals("Ground")) {
                    JsonArray tileData = firstLayer.getAsJsonArray("data");
                    int width = firstLayer.get("width").getAsInt();
                    int height = firstLayer.get("height").getAsInt();

                    // Crea l'array bidimensionale
                    mapTiles = new MapTile[height][width];
                    int index = 0;
                    for (int row = 0; row < height; row++) {
                        for (int col = 0; col < width; col++) {
                            int tileNumber = tileData.get(index).getAsInt();
                            if (tileNumber == 2)
                                mapTiles[row][col] = new MapTile(true, tileNumber,col* Settings.TILE_SIZE,
                                        row*Settings.TILE_SIZE);
                            else
                                mapTiles[row][col] = new MapTile(false, tileNumber,col* Settings.TILE_SIZE,
                                        row*Settings.TILE_SIZE);
                            index++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public MapTile[][] getMapTiles() {
        return mapTiles;
    }
}
