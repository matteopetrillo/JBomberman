package it.petrillo.jbomberman.model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameSettings.*;

public class AdvancedEnemy extends Enemy {

    /**
     * Constructs an Enemy instance with the specified initial position.
     *
     * @param x The X-coordinate of the enemy's initial position.
     * @param y The Y-coordinate of the enemy's initial position.
     */
    public AdvancedEnemy(int x, int y) {
        super(x, y);
        entityScale = 3.5d;
        characterSpeed = 2;
        xCollisionOffset = (int) (1*entityScale);
        yCollisionOffset = (int) (3*entityScale);
        collisionBox.setLocation(super.x+xCollisionOffset,super.y+yCollisionOffset);
        collisionBox.setSize((int) (12*SCALE), (int) (10*SCALE));
        defaultSpriteWidth = 16;
        defaultSpriteHeight = 16;
        health = 2;
        animationSpeed = 15;
        scoreValue = 250;
        movingDirection = pickRandomDirection();
        loadSprites( "/Sprites_Enemy2.png");
    }

    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[1][10];
        for (int i = 0; i < spriteAnimation[0].length; i++) {
            spriteAnimation[0][i] = spriteSheet.getSubimage(16*i,0,16,16);
        }
    }

    @Override
    public void update() {
        if (hittedTimer > 0)
            hittedTimer--;
        if (health > 0) {
            updatePosition();
            animationTick++;
            scoreTextY = y;
            if (animationTick >= animationSpeed) {
                animationTick = 0;
                animationIndex++;
                if (animationIndex >= (spriteAnimation[0].length-1))
                    animationIndex = 0;
            }
        }
        else {
            if (hittedTimer <= 0) {
                visible = false;
                scoreTextY--;
                Timer cleanTimer = new Timer(1500, e -> {
                    setToClean(true);
                });
                cleanTimer.setRepeats(false);
                cleanTimer.start();
            }
        }
    }
}
