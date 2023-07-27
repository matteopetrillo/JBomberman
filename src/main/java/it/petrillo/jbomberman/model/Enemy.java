package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.GameUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class Enemy extends GameCharacter { //TODO implementare diverse classi di nemici o id nemici
    public Enemy(int x, int y) {
        super(x, y);
        entityScale = 3d;
        entitySpeed = 3;
        collisionBox.setLocation(super.x+xCollisionOffset,super.y+yCollisionOffset);
        collisionBox.setSize((int)(9*entityScale), (int) (5*entityScale));
        health = 1;
        animationSpeed = 15;
        movingDirection = pickRandomDirection();
        //loadSprites();


    }
    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            g.drawImage(spriteAnimation[getAniIndexByDirection()][animationIndex],x,y,
                    (int) (16*entityScale),(int)(24*entityScale), null);
        }
    }

    @Override
    protected void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[4][4];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,24*i,16,24);
            }
        }
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
