package it.petrillo.jbomberman.model;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GameMap {

    private static GameMap gameMapInstance;
    private MapTile[][] map = new MapTile[MAP_ROWS][MAP_COLUMNS];
    private BufferedImage tileSet;
    private Map<Integer, BufferedImage> tileImgReference = new HashMap<>();
    private List<GameObject> objects = new ArrayList<>();

    private GameMap() {}

    public void initMap(String jsonPath) {

        List<String> fields = List.of("map_tile_set","map_ids","soft_blocks_spawn");
        Map<String, JsonElement> mapSetting = getMultipleJsonFields(jsonPath,fields);
        String tileSetPath = mapSetting.get("map_tile_set").getAsString();
        this.tileSet = getImg(tileSetPath);
        initTileSet(tileSet);
        JsonArray tileIdArray = mapSetting.get("map_ids").getAsJsonArray();
        initTiles(tileIdArray);
        JsonArray softBlocksArray = mapSetting.get("soft_blocks_spawn").getAsJsonArray();
        initSoftBlocks(softBlocksArray);

    }

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

    private void initSoftBlocks(JsonArray jsonArray) {
        for (JsonElement element : jsonArray) {
            int x = element.getAsJsonObject().get("x").getAsInt();
            int y = element.getAsJsonObject().get("y").getAsInt();
            SoftBlock softBlock = new SoftBlock(x*TILE_SIZE,y*TILE_SIZE,true);
            if (y-1 >=0) {
                MapTile upperTile = map[y-1][x];
                if (upperTile.getTileId() == 9)
                    softBlock.setHasShadow(true);
            }
            objects.add(softBlock);
        }
    }

    public void drawMap(Graphics2D g2d) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                MapTile tile = map[i][j];
                BufferedImage tileImg = tileImgReference.get(tile.getTileId());
                g2d.drawImage(tileImg,j*TILE_SIZE,i*TILE_SIZE,TILE_SIZE,TILE_SIZE,null);
            }
        }
        objects.stream()
                .filter(e -> e.isVisible())
                .forEach(e -> e.draw(g2d));
    }

    public void addObject(GameObject o) {
        objects.add(o);
    }

    public Optional<GameObject> getObjectFromCoords(int x, int y) {
        return objects.stream()
                    .filter(obj -> obj.getX()/TILE_SIZE == x && obj.getY()/TILE_SIZE == y)
                    .findAny();
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public MapTile getTileFromCoords(int x, int y) {
        return map[y][x];
    }
    public static GameMap getInstance() {
        if (gameMapInstance == null)
            gameMapInstance = new GameMap();

        return gameMapInstance;
    }


}
