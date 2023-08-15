package it.petrillo.jbomberman.model;


import com.google.gson.JsonArray;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static it.petrillo.jbomberman.util.GameSettings.*;

/**
 * The GameMap class represents the game mapImg containing MapTiles and tile images.
 * It manages the initialization, drawing, and retrieval of mapImg tiles.
 */
public class GameMap {

    private static GameMap gameMapInstance;
    private BufferedImage mapImg;
    private MapTile[][] tiles = new MapTile[MAP_ROWS][MAP_COLUMNS];

    private GameMap() {}

    /**
     * Initializes the game mapImg using data from a JSON file.
     *
     *
     */
    public void initMap(String imgPath, JsonArray mapData) {
        List<String> fields = List.of("mapImg", "map_data");
        this.mapImg = getImg(imgPath);
        analyzeMapData(mapData);
    }

    /**
     * Initializes individual mapImg tiles based on tile IDs from a JSON array.
     *
     * @param jsonArray The JSON array containing tile IDs.
     */
    private void analyzeMapData(JsonArray jsonArray) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int tileId = jsonArray.get(i).getAsJsonArray().get(j).getAsInt();
                if (tileId == 1)
                    tiles[i][j] = new MapTile(j*TILE_SIZE,i*TILE_SIZE,TileType.WALL,false);
                else
                    tiles[i][j] = new MapTile(j*TILE_SIZE,i*TILE_SIZE, TileType.FLOOR,true);
            }
        }
    }

    /**
     * Draws the game mapImg on the specified graphics context.
     *
     * @param g2d The Graphics2D object used for drawing.
     */
    public void drawMap(Graphics2D g2d) {
        g2d.drawImage(mapImg,0,0,SCREEN_WIDTH,SCREEN_HEIGHT,null);
    }

    /**
     * Returns a MapTile object based on coordinates (x, y) within the mapImg.
     *
     * @param x The x-coordinate of the tile within the mapImg.
     * @param y The y-coordinate of the tile within the mapImg.
     * @return The MapTile object at the specified coordinates.
     */
    public MapTile getTileFromCoords(int x, int y) {
        return tiles[y][x];
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
     * Clears the mapImg by setting all mapImg tiles to null.
     */
    public void clearMap() {
        for (MapTile[] mapTiles : tiles) {
            Arrays.fill(mapTiles, null);
        }
    }

}
