package it.petrillo.jbomberman.model;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static it.petrillo.jbomberman.util.GameSettings.*;

// TODO Immettere nel JSON l'id del tipo di tile così da rendere modulare l'assegnazione delle specifiche

/**
 * The GameMap class represents the game map containing MapTiles and tile images.
 * It manages the initialization, drawing, and retrieval of map tiles.
 */
public class GameMap {

    private static GameMap gameMapInstance;
    private MapTile[][] map = new MapTile[MAP_ROWS][MAP_COLUMNS];
    private Map<Integer, BufferedImage> tileImgReference = new HashMap<>();

    private GameMap() {}

    /**
     * Initializes the game map using data from a JSON file.
     *
     * @param jsonPath The path to the JSON file containing map configuration.
     */
    public void initMap(String jsonPath) {
        List<String> fields = List.of("map_tile_set", "map_ids");
        Map<String, JsonElement> mapSetting = getMultipleJsonFields(jsonPath, fields);
        String tileSetPath = mapSetting.get("map_tile_set").getAsString();
        BufferedImage tileSet = getImg(tileSetPath);
        initTileSet(tileSet);
        JsonArray tileIdArray = mapSetting.get("map_ids").getAsJsonArray();
        initTiles(tileIdArray);
    }

    /**
     * Initializes individual map tiles based on tile IDs from a JSON array.
     *
     * @param jsonArray The JSON array containing tile IDs.
     */
    private void initTiles(JsonArray jsonArray) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int tileId = jsonArray.get(i).getAsJsonArray().get(j).getAsInt();
                if (tileId == 10 || tileId == 16)
                    map[i][j] = new MapTile(j*TILE_SIZE,i*TILE_SIZE,tileId,true);
                else
                    map[i][j] = new MapTile(j*TILE_SIZE,i*TILE_SIZE,tileId,false);
            }
        }
    }

    /**
     * Initializes the tile image reference map using a tile set image.
     *
     * @param tileSet The tile set image used for mapping tile IDs to images.
     */
    private void initTileSet(BufferedImage tileSet) {
        int width = tileSet.getWidth();
        int height = tileSet.getHeight();
        int index = 1;
        for (int j = 0; j < height / DEFAULT_TILE_SIZE; j++) {
            for (int i = 0; i < width / DEFAULT_TILE_SIZE; i++) {
                BufferedImage subimg = tileSet.getSubimage(i * DEFAULT_TILE_SIZE, j * DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE);
                tileImgReference.put(index, subimg);
                index++;
            }
        }
    }

    /**
     * Draws the entire game map on the specified graphics context.
     *
     * @param g2d The Graphics2D object used for drawing.
     */
    public void drawMap(Graphics2D g2d) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                MapTile tile = map[i][j];
                BufferedImage tileImg = tileImgReference.get(tile.getTileId());
                g2d.drawImage(tileImg,j*TILE_SIZE,i*TILE_SIZE,TILE_SIZE,TILE_SIZE,null);
            }
        }

    }

    /**
     * Returns a MapTile object based on coordinates (x, y) within the map.
     *
     * @param x The x-coordinate of the tile within the map.
     * @param y The y-coordinate of the tile within the map.
     * @return The MapTile object at the specified coordinates.
     */
    public MapTile getTileFromCoords(int x, int y) {
        return map[y][x];
    }

    /**
     * Returns the singleton instance of the GameMap class.
     *
     * @return The singleton instance of GameMap.
     */
    public static GameMap getInstance() {
        if (gameMapInstance == null)
            gameMapInstance = new GameMap();

        return gameMapInstance;
    }

    /**
     * Clears the map by setting all map tiles to null.
     */
    public void clear() {
        for (MapTile[] mapTiles : map) {
            Arrays.fill(mapTiles, null);
        }
    }

}
