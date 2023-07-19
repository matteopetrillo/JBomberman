package it.petrillo.jbomberman.model;

public class GameEntityFactory {

    public static Enemy createEnemy(int x, int y, EnemyType enemyType, boolean visible) {
        return new Enemy(x,y,visible);
    }

    public static Bomb createBomb(int x, int y, boolean visible) {
        return new Bomb(x,y,visible);
    }
}
