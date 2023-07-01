package it.petrillo.jbomberman.model;

public class Enemy extends GameEntity {
    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void onCollision(Collidable other) {

    }
}
