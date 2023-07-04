package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.GameUtils.ObjectVisibility;

public class Enemy extends GameEntity {
    public Enemy(int x, int y, ObjectVisibility visibility) {
        super(x, y, visibility);
    }


    @Override
    public void onCollision(Collidable other) {
    }

}
