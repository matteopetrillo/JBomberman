package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.*;
import it.petrillo.jbomberman.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class BombManager {

    private List<Bomb> bombs = new ArrayList();
    private List<Bomb> bombsExploded = new ArrayList<>();
    private GameMap gameMap = GameMap.getInstance();
    private Bomberman bomberman = Bomberman.getPlayerInstance();

    public void newBomb(int x, int y) {
        bombs.add(GameEntityFactory.createBomb(x,y,true));
    }

    public void updateBombs() {
        if (!bombs.isEmpty()) {
            for (Bomb bomb : bombs) {
                if (!bomb.isExploded())
                    bomb.update();
                else {
                    bombExplosion((int) (bomb.getY()/Settings.TILE_SIZE),(int) (bomb.getX()/Settings.TILE_SIZE), bomb.getRadius());
                    bombsExploded.add(bomb);
                }
            }
        }
        for (Bomb bomb : bombsExploded) {
            bombs.remove(bomb);
        }
        bombsExploded.clear();
    }


    public void bombExplosion(int y, int x, int bombRadius) {
        int[][] directions = { {1, 0}, {-1, 0}, {0, -1}, {0, 1} };
        boolean playerHitted = false;

        for (int[] direction : directions) {
            int dy = direction[0];
            int dx = direction[1];

            for (int i = 0; i <= bombRadius; i++) {
                int newY = y + i * dy;
                int newX = x + i * dx;

                int playerX = (int)(bomberman.getCollisionBox().x/Settings.TILE_SIZE);
                int playerY = (int)(bomberman.getCollisionBox().y/Settings.TILE_SIZE);

                MapTile tile = gameMap.getTileFromCoords(newX,newY);
                if (newX == playerX && newY == playerY && !playerHitted) {
                    bomberman.hitPlayer();
                    playerHitted = true;
                }

                if (tile.isDestroyable()) {
                    tile.setTileID(2);
                    tile.setDestroyable(false);
                    tile.setWalkable(true);
                    gameMap.setMapTile(tile, newX, newY);

                } else if (tile.isWalkable())
                    continue;
                else
                    break;
            }
        }
    }

    public List<Bomb> getBombs() {
        return bombs;
    }
}
