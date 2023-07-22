package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.Enemy;
import it.petrillo.jbomberman.model.EnemyType;
import it.petrillo.jbomberman.model.GameEntityFactory;

import java.util.*;

public class EnemyManager {

    private List<Enemy> enemies = new ArrayList<>();




    public List<Enemy> getEnemies() {
        return enemies;
    }
}
