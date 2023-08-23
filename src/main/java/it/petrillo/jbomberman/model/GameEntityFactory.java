package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.model.characters.AdvancedEnemy;
import it.petrillo.jbomberman.model.characters.BasicEnemy;
import it.petrillo.jbomberman.model.characters.Enemy;
import it.petrillo.jbomberman.model.objects.*;
import it.petrillo.jbomberman.util.Direction;

import java.util.ArrayList;
import java.util.List;

import static it.petrillo.jbomberman.util.GameConstants.*;

/**
 * The GameEntityFactory class provides static methods to create various game entities such as
 * SoftBlocks, Bombs, Explosions, BasicEnemies, and PowerUps.
 * It also allows setting the sprite sheets for the entities.
 */
public class GameEntityFactory {

    private static String bombSpriteSheet;
    private static String softBlockSpriteSheet;
    private static String explosionSpriteSheet;

    /**
     * Creates and returns a new SoftBlock entity.
     *
     * @param x The x-coordinate of the SoftBlock.
     * @param y The y-coordinate of the SoftBlock.
     * @return The newly created SoftBlock entity.
     */
    public static SoftBlock createSoftBlock(int x, int y, boolean hasShadow) {
        return new SoftBlock(x*TILE_SIZE,y*TILE_SIZE,softBlockSpriteSheet, hasShadow);
    }

    /**
     * Creates and returns a new Bomb entity.
     *
     * @param x The x-coordinate of the Bomb.
     * @param y The y-coordinate of the Bomb.
     * @return The newly created Bomb entity.
     */
    public static Bomb createBomb(int x, int y) {
        return new Bomb(x* TILE_SIZE,y*TILE_SIZE, bombSpriteSheet);
    }

    /**
     * Creates and returns a new Explosion entity.
     *
     * @param x          The x-coordinate of the Explosion.
     * @param y          The y-coordinate of the Explosion.
     * @param directions The directions in which the Explosion should be drawn.
     * @return The newly created Explosion entity.
     */
    public static Explosion createExplosion(int x, int y, List<Direction> directions) {
        return new Explosion(x*TILE_SIZE,y*TILE_SIZE,explosionSpriteSheet,directions);
    }

    /**
     * Creates and returns a new Enemy entity.
     *
     * @param x The x-coordinate of the BasicEnemy.
     * @param y The y-coordinate of the BasicEnemy.
     * @return The newly created BasicEnemy entity.
     */
    public static Enemy createEnemy(int x, int y, EnemyType type) {
        return switch (type) {
            case BASIC -> new BasicEnemy(x * TILE_SIZE, y * TILE_SIZE);
            case ADVANCED -> new AdvancedEnemy(x * TILE_SIZE, y * TILE_SIZE);
        };
    }

    /**
     * Creates and returns a new PowerUp entity based on the specified type.
     *
     * @param x    The x-coordinate of the PowerUp.
     * @param y    The y-coordinate of the PowerUp.
     * @param type The type of PowerUp to create ("HEART", "BOMB", "PORTAL").
     * @return The newly created PowerUp entity, or null if the type is not recognized.
     */
    public static PowerUp createPowerUp(int x, int y, PowerUpType type) {
        return switch (type) {
            case HEART -> new LifeAdder(x * TILE_SIZE, y * TILE_SIZE);
            case BOMB -> new BombAdder(x * TILE_SIZE, y * TILE_SIZE);
            case PORTAL -> new LevelAdvancer(x * TILE_SIZE, y * TILE_SIZE);
            case CAKE -> new ScoreAdder(x * TILE_SIZE, y * TILE_SIZE);
        };
    }

    /**
     * Sets the sprite sheet for Bomb entities.
     *
     * @param bombSpriteSheet The path to the Bomb sprite sheet.
     */
    public static void setBombSpriteSheet(String bombSpriteSheet) {
        GameEntityFactory.bombSpriteSheet = bombSpriteSheet;
    }

    /**
     * Sets the sprite sheet for SoftBlock entities.
     *
     * @param softBlockSpriteSheet The path to the SoftBlock sprite sheet.
     */
    public static void setSoftBlockSpriteSheet(String softBlockSpriteSheet) {
        GameEntityFactory.softBlockSpriteSheet = softBlockSpriteSheet;
    }

    /**
     * Sets the sprite sheet for Explosion entities.
     *
     * @param explosionSpriteSheet The path to the Explosion sprite sheet.
     */
    public static void setExplosionSpriteSheet(String explosionSpriteSheet) {
        GameEntityFactory.explosionSpriteSheet = explosionSpriteSheet;
    }
}
