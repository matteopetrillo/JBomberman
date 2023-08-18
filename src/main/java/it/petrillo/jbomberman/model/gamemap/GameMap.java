package it.petrillo.jbomberman.model.gamemap;


import com.google.gson.JsonArray;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static it.petrillo.jbomberman.util.GameConstants.*;
import static it.petrillo.jbomberman.util.UtilFunctions.getImg;

/**
 * The GameMap class represents the game map containing MapTiles for handling
 * interaction with GameEntities.
 * It manages the initialization, drawing, and retrieval of map and tiles.
 */
public class GameMap {

    private static GameMap gameMapInstance;
    private BufferedImage mapImg;
    private final MapTile[][] tiles = new MapTile[MAP_ROWS][MAP_COLUMNS];

    private GameMap() {}

    /**
     * Initializes the game map using an image and map data from a JSON array.
     *
     * @param imgPath  The path to the image representing the map.
     * @param mapData  The JSON array containing map tile data.
     */
    public void initMap(String imgPath, JsonArray mapData) {
        this.mapImg = getImg(imgPath);
        analyzeMapData(mapData);
    }

    /**
     * Initializes individual map tiles based on tile IDs from a JSON array.
     *
     * @param jsonArray The JSON array containing tile IDs.
     */
    private void analyzeMapData(JsonArray jsonArray) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int tileId = jsonArray.get(i).getAsJsonArray().get(j).getAsInt();
                if (tileId == 1)
                    tiles[i][j] = new MapTile(j*TILE_SIZE,i*TILE_SIZE, MapTile.TileType.WALL,false);
                else
                    tiles[i][j] = new MapTile(j*TILE_SIZE,i*TILE_SIZE, MapTile.TileType.FLOOR,true);
            }
        }
    }

    /**
     * Draws the game map on the specified graphics context.
     *
     * @param g2d The Graphics2D object used for drawing.
     */
    public void drawMap(Graphics2D g2d) {
        g2d.drawImage(mapImg,0,0,SCREEN_WIDTH,SCREEN_HEIGHT,null);
    }

    /**
     * Returns a MapTile object based on coordinates (x, y) within the map.
     *
     * @param x The x-coordinate of the tile within the map.
     * @param y The y-coordinate of the tile within the map.
     * @return The MapTile object at the specified coordinates.
     */
    public MapTile getTileFromCoords(int x, int y) {
        return tiles[y][x];
    }

    /**
     * Clears the map by setting all tiles to null.
     */
    public void clearMap() {
        for (MapTile[] mapTiles : tiles) {
            Arrays.fill(mapTiles, null);
        }
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

}
