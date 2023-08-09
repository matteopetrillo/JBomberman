package it.petrillo.jbomberman.model;


import java.util.ArrayList;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GameEntityFactory {

    private static String bombSpriteSheet;
    private static String softBlockSpriteSheet;
    private static String explosionSpriteSheet;

    public static SoftBlock createSoftBlock(int x, int y) {
        return new SoftBlock(x*TILE_SIZE,y*TILE_SIZE,softBlockSpriteSheet);
    }
    public static Bomb createBomb(int x, int y) {
        return new Bomb(x* TILE_SIZE,y*TILE_SIZE, bombSpriteSheet);
    }

    public static Explosion createExplosion(int x, int y, ArrayList<Direction> directions) {
        return new Explosion(x*TILE_SIZE,y*TILE_SIZE,explosionSpriteSheet,directions);
    }
    public static BasicEnemy createBasicEnemy(int x, int y) {
        return new BasicEnemy(x*TILE_SIZE,y*TILE_SIZE);
    }

    public static PowerUp createPowerUp(int x, int y, String type) {
        if (type.equals("HEART"))
            return new LifeAdder(x*TILE_SIZE, y*TILE_SIZE);
        else if (type.equals("BOMB"))
            return new BombAdder(x*TILE_SIZE, y*TILE_SIZE);
        return null;
    }
    public static void setBombSpriteSheet(String bombSpriteSheet) {
        GameEntityFactory.bombSpriteSheet = bombSpriteSheet;
    }
    public static void setSoftBlockSpriteSheet(String softBlockSpriteSheet) {
        GameEntityFactory.softBlockSpriteSheet = softBlockSpriteSheet;
    }

    public static void setExplosionSpriteSheet(String explosionSpriteSheet) {
        GameEntityFactory.explosionSpriteSheet = explosionSpriteSheet;
    }
}
