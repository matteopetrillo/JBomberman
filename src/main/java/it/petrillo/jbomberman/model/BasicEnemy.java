package it.petrillo.jbomberman.model;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameSettings.*;

/**
 * The BasicEnemy class represents a basic enemy character in the game.
 * It extends the Enemy class and encapsulates the behavior of a simple enemy.
 */
public class BasicEnemy extends Enemy {

    /**
     * Constructs a BasicEnemy instance with the specified initial position.
     *
     * @param x The X-coordinate of the enemy's initial position.
     * @param y The Y-coordinate of the enemy's initial position.
     */
    public BasicEnemy(int x, int y) {
        super(x, (int) (y-8*SCALE));
        entityScale = 3.5d;
        characterSpeed = 2;
        yCollisionOffset = (int)(16*entityScale);
        xCollisionOffset = (int)(4*entityScale);
        collisionBox = new Area(new Rectangle(super.x+xCollisionOffset,super.y+yCollisionOffset,
                (int)(10*entityScale), (int) (9*entityScale)));
        defaultSpriteWidth = 16;
        defaultSpriteHeight = 24;
        health = 1;
        animationSpeed = 5;
        scoreValue = 100;
        loadSprites("/Sprites_Enemy1.png");
    }

    /**
     * Loads the sprite animation of the BasicEnemy from the specified path.
     *
     * @param path The path to the sprite file.
     */
    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[4][4];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(defaultSpriteWidth*j,defaultSpriteHeight*i,
                        defaultSpriteWidth,defaultSpriteHeight);
            }
        }
    }

}
