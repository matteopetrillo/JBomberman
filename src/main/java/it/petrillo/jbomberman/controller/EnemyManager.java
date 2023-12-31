package it.petrillo.jbomberman.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import it.petrillo.jbomberman.model.*;
import it.petrillo.jbomberman.model.characters.AdvancedEnemy;
import it.petrillo.jbomberman.model.characters.BasicEnemy;
import it.petrillo.jbomberman.model.characters.Bomberman;
import it.petrillo.jbomberman.model.characters.Enemy;
import it.petrillo.jbomberman.model.objects.EnemyType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages the creation, updating, and removal of enemy entities in the game.
 * It handles different types of enemies and their interactions with the game.
 */
public class EnemyManager {
    private static EnemyManager enemyManagerInstance;
    private final Bomberman bomberman = Bomberman.getPlayerInstance();
    private final List<Enemy> enemies = new ArrayList<>();
    private final CollisionManager collisionManager = CollisionManager.getInstance();

    private EnemyManager() {}

    /**
     * Initializes the enemy entities based on the provided JSON data.
     *
     * @param jsonArray A JSON array containing enemy data with coordinates and types.
     */
    public void initEnemies(JsonArray jsonArray) {
        for (JsonElement element : jsonArray) {
            int x = element.getAsJsonObject().get("x").getAsInt();
            int y = element.getAsJsonObject().get("y").getAsInt();
            EnemyType enemyType = EnemyType.valueOf(element.getAsJsonObject().get("type").getAsString());
            Enemy enemy = GameEntityFactory.createEnemy(x, y, enemyType);
            enemies.add(enemy);

        }
        for (Enemy enemy : enemies) {
            enemy.setCollisionListener(collisionManager);
            collisionManager.addCollidable(enemy);
        };
    }

    /**
     * Updates the state of enemy entities and removes defeated enemies.
     */
    public void updateEnemies() {
        cleanEnemies();
        enemies.forEach(Enemy::update);
    }

    /**
     * Cleans up defeated enemy entities from the list adding their score value to player's score.
     */
    private void cleanEnemies() {
        List<Enemy> killedEnemy = enemies.stream()
                .filter(GameEntity::isToClean)
                .collect(Collectors.toCollection(ArrayList::new));

        for (Enemy e : killedEnemy) {
            bomberman.alterScore(e.getScoreValue());
            enemies.remove(e);
        }
    }

    /**
     * Returns the list of active enemy entities.
     *
     * @return The list of active enemy entities.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Returns the singleton instance of the EnemyManager.
     *
     * @return The singleton instance of EnemyManager.
     */
    public static EnemyManager getInstance() {
        if (enemyManagerInstance == null)
            enemyManagerInstance = new EnemyManager();
        return enemyManagerInstance;
    }

}
