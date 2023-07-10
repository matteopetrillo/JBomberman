package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.GameUtils;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GameEntityFactory {

    public static Enemy createEnemy(int x, int y, EnemyType enemyType, boolean visibility) {
        return new Enemy(x,y,visibility);
    }

}
