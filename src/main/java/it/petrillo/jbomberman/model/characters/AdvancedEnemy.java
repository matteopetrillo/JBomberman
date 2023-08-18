package it.petrillo.jbomberman.model.characters;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.UtilFunctions.getImg;

/**
 * The BasicEnemy class represents an advanced enemy character in the game.
 * It extends the Enemy class and encapsulates the behavior of an advanced enemy.
 */
public class AdvancedEnemy extends Enemy {

    /**
     * Constructs an AdvancedEnemy instance with the specified initial position.
     *
     * @param x The X-coordinate of the enemy's initial position.
     * @param y The Y-coordinate of the enemy's initial position.
     */
    public AdvancedEnemy(int x, int y) {
        super(x, y);
        entityScale = 3.5d;
        characterSpeed = 2;
        xCollisionOffset = (int) (2*entityScale);
        yCollisionOffset = (int) (1*entityScale);
        collisionBox = new Area(new Rectangle(super.x+xCollisionOffset,super.y+yCollisionOffset,
                (int) (12*entityScale), (int) (13*entityScale)));
        defaultSpriteWidth = 16;
        defaultSpriteHeight = 16;
        health = 2;
        animationSpeed = 15;
        scoreValue = 250;
        movingDirection = pickRandomDirection();
        loadSprites("/Sprites/Enemies/Sprites_Enemy2.png");
    }

    /**
     * Loads the sprite images for the enemy character from the specified image path.
     *
     * @param path The path to the sprite image file.
     */
    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[1][10];
        for (int i = 0; i < spriteAnimation[0].length; i++) {
            if (spriteSheet != null) {
                spriteAnimation[0][i] = spriteSheet.getSubimage(16*i,0,16,16);
            }
        }
    }

}
