package it.petrillo.jbomberman.model.objects;

import it.petrillo.jbomberman.model.interfaces.Explodable;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameConstants.*;
import static it.petrillo.jbomberman.util.UtilFunctions.getImg;

/**
 * The Bomb class represents a bomb object in the game.
 * It extends the GameObject class and implements the Explodable interface.
 * This class encapsulates the behavior and attributes of a bomb.
 */
public class Bomb extends GameObject implements Explodable {
    private int timer = 80;
    private boolean explosionStarted;
    private boolean isExploding;

    /**
     * Constructs a Bomb instance with the specified initial position and sprite sheet path.
     *
     * @param x              The X-coordinate of the bomb's initial position.
     * @param y              The Y-coordinate of the bomb's initial position.
     * @param spriteSheetPath The path to the sprite sheet for the bomb's animation.
     */
    public Bomb(int x, int y, String spriteSheetPath) {
        super(x, y);
        isSolid = false;
        animationSpeed = 15;
        visible = true;
        loadSprites(spriteSheetPath);
    }

    /**
     * Draws the Bomb object on the provided graphics.
     * Handles animation and displays the bomb or its explosion.
     *
     * @param g The Graphics2D object to draw on.
     */
    @Override
    public void draw(Graphics2D g) {
        if (isVisible()) {
            if (isExploding)
                g.drawImage(spriteAnimation[1][animationIndex], x, y, TILE_SIZE, TILE_SIZE, null);
            else
                g.drawImage(spriteAnimation[0][animationIndex], x, y, TILE_SIZE, TILE_SIZE, null);
        }
    }

    /**
     * Loads the sprite animation frames for the bomb from the specified sprite sheet path.
     *
     * @param path The path to the sprite sheet file.
     */
    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        int rows = spriteSheet.getHeight()/DEFAULT_TILE_SIZE;
        int height = spriteSheet.getWidth()/DEFAULT_TILE_SIZE;
        spriteAnimation = new BufferedImage[rows][height];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,16*i,16,16);
            }
        }
    }

    /**
     * Updates the state of the bomb.
     * Manages the timer, explosion animation, and visibility.
     */
    @Override
    public void update() {
        if(timer > 0) {
            timer--;
            if (timer <= 0) {
                isExploding = true;
                animationSpeed = 5;
            }
        }
        updateAnimation();
    }

    /**
     * Updates the animation state of the bomb.
     * Handles animation timing and sets the bomb to be cleaned up when appropriate.
     */
    public void updateAnimation() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 5)
                if (!isExploding)
                    animationIndex = 0;
                else {
                    setToClean(true);
                    visible = false;
                }
        }
    }

    /**
     * Checks if the explosion has started.
     *
     * @return True if the explosion has started, otherwise false.
     */
    public boolean isExplosionStarted() {
        return explosionStarted;
    }

    /**
     * Sets the state of the explosion.
     *
     * @param explosionStarted True to indicate that the explosion has started, false otherwise.
     */
    public void setExplosionStarted(boolean explosionStarted) {
        this.explosionStarted = explosionStarted;
    }

    /**
     * Sets whether the bomb is currently exploding.
     *
     * @param exploding True if the bomb is exploding, otherwise false.
     */
    @Override
    public void setExploding(boolean exploding) {
        this.isExploding = exploding;
    }

    /**
     * Checks if the bomb is currently exploding.
     *
     * @return True if the bomb is exploding, otherwise false.
     */
    @Override
    public boolean isExploding() {
        return this.isExploding;
    }

}
