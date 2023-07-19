package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.CollisionListener;
import it.petrillo.jbomberman.model.GameEntity;
import it.petrillo.jbomberman.model.GameMap;
import it.petrillo.jbomberman.model.MapTile;
import it.petrillo.jbomberman.util.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionManager implements CollisionListener {

    private static CollisionManager collisionManagerInstance;
    private List<GameEntity> gameEntities = new ArrayList();
    private static GameMap gameMap;

    private CollisionManager() {}
    public void setGameMap(GameMap gameMap) {
        CollisionManager.gameMap = gameMap;}
    public void addCollidable(GameEntity gameEntity) {
        gameEntities.add(gameEntity);
    }
    private static boolean isWalkable(int x, int y) {
        int tileSize = Settings.TILE_SIZE;
        int xIndex = x / tileSize;
        int yIndex = y / tileSize;
        return gameMap.getTileFromCoords(xIndex,yIndex).isWalkable();
    }
    @Override
    public boolean canMoveThere(int dx, int dy, Rectangle collisionBox) {
        int width = (int) collisionBox.getWidth();
        int height = (int) collisionBox.getHeight();
        int x = (int) collisionBox.getX()+dx;
        int y = (int) collisionBox.getY()+dy;

        return isWalkable(x, y) && isWalkable(x + width, y + width) &&
                isWalkable(x + width, y) && isWalkable(x, y + height);
    }

    public static CollisionManager getInstance() {
        if (collisionManagerInstance == null)
            collisionManagerInstance = new CollisionManager();
        return collisionManagerInstance;
    }
}
