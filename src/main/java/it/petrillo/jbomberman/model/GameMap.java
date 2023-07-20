package it.petrillo.jbomberman.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.petrillo.jbomberman.util.Settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GameMap {

    private static GameMap gameMapInstance;
    private MapTile[][] mapTiles;

    private GameMap() {}

    public void initMap(String filePath) {
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
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            int tileNumber = tileData.get(index).getAsInt();
                            if (tileNumber == 2)
                                mapTiles[i][j] = new MapTile(true, false, tileNumber,j* Settings.TILE_SIZE,
                                        i*Settings.TILE_SIZE);
                            else if (tileNumber == 9) {
                                mapTiles[i][j] = new MapTile(false, true, tileNumber,j* Settings.TILE_SIZE,
                                        i*Settings.TILE_SIZE);
                            }
                            else {
                                mapTiles[i][j] = new MapTile(false, false, tileNumber, j * Settings.TILE_SIZE,
                                        i * Settings.TILE_SIZE);
                            }
                            index++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setMapTile(MapTile tile, int x, int y) {
        mapTiles[y][x] = tile;
    }

    public MapTile[][] getMapTiles() {
        return mapTiles;
    }
    public MapTile getTileFromCoords(int x, int y) {
        return mapTiles[y][x];
    }

    public static GameMap getInstance() {
        if (gameMapInstance == null)
            gameMapInstance = new GameMap();

        return gameMapInstance;
    }

}
