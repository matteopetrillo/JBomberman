package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.Enemy;
import it.petrillo.jbomberman.model.EnemyType;
import it.petrillo.jbomberman.model.GameEntityFactory;
import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.Settings;

import javax.xml.stream.events.EndElement;
import java.util.*;

public class EnemyManager {

    private List<Enemy> enemies = new ArrayList<>();


    public void initEnemies(Settings settings) {
        Map<EnemyType,Integer> enemiesToCreate = settings.getEnemies();
        for (Map.Entry<EnemyType, Integer> entry : enemiesToCreate.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                Random rd = new Random();
                enemies.add(GameEntityFactory.createEnemy(rd.nextInt(15*32)+1*32,rd.nextInt(10*32)+1*32,entry.getKey(), GameUtils.ObjectVisibility.VISIBLE));
            }
        }
    }


    public List<Enemy> getEnemies() {
        return enemies;
    }
}
