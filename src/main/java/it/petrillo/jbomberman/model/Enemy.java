package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.GameUtils;

import java.awt.*;
import java.util.Random;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class Enemy extends GameCharacter {
    public Enemy(int x, int y) {
        super(x, y);
        entityScale = 3d;
        entitySpeed = 3;
        collisionBox.setLocation(super.x+xCollisionOffset,super.y+yCollisionOffset);
        collisionBox.setSize((int)(9*entityScale), (int) (5*entityScale));
        health = 1;
        animationSpeed = 15;
        movingDirection = pickRandomDirection();


    }
    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    protected void loadSprites(String path) {

    }

    @Override
    public void updateStatus() {

    }

    private Direction pickRandomDirection() {
        Random rd = new Random();
        int n = rd.nextInt(1,4);
        switch (n) {
            case 1 -> {
                return Direction.UP;
            }
            case 2 -> {
                return Direction.DOWN;
            }
            case 3 -> {
                return Direction.LEFT;
            }
            case 4 -> {
                return Direction.RIGHT;
            }
            default -> {
                return Direction.DOWN;
            }
        }
    }
}
