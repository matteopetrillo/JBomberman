package it.petrillo.jbomberman.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import it.petrillo.jbomberman.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class EnemyManager {
    private static EnemyManager enemyManagerInstance;
    private final Bomberman bomberman = Bomberman.getPlayerInstance();
    private final List<Enemy> enemies = new ArrayList<>();
    private final CollisionManager collisionManager = CollisionManager.getInstance();
    public void initEnemies(JsonArray jsonArray) {
        for (JsonElement element : jsonArray) {
            int x = element.getAsJsonObject().get("x").getAsInt();
            int y = element.getAsJsonObject().get("y").getAsInt();
            String enemyType = element.getAsJsonObject().get("type").getAsString();
            if (enemyType.equals("BASIC")) {
                BasicEnemy basicEnemy = GameEntityFactory.createBasicEnemy(x, y);
                basicEnemy.setCollisionListener(collisionManager);
                enemies.add(basicEnemy);
            } else if (enemyType.equals("ADVANCED")) {
                // TODO IMPLEMENTARE NEMICI AVANZATI
            }

        }
        enemies.forEach(collisionManager::addCollidable);
    }

    public void updateEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy.isVisible())
                enemy.update();
        }
        cleanEnemies();
    }

    private void cleanEnemies() {
        List<Enemy> killedEnemy = enemies.stream()
                .filter(GameEntity::isToClean)
                .collect(Collectors.toCollection(ArrayList::new));

        for (Enemy e : killedEnemy) {
            if (e instanceof BasicEnemy)
                bomberman.alterScore(100);
            // TODO IMPLEMENTARE ANCHE PER AVANZATO
            enemies.remove(e);
        };
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public static EnemyManager getInstance() {
        if (enemyManagerInstance == null)
            enemyManagerInstance = new EnemyManager();
        return enemyManagerInstance;
    }

    public void clear() {
        enemies.clear();
    }

}
