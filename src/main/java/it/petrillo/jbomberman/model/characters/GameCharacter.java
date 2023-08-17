package it.petrillo.jbomberman.model.characters;

import it.petrillo.jbomberman.model.interfaces.Collidable;
import it.petrillo.jbomberman.util.CollisionListener;
import it.petrillo.jbomberman.model.GameEntity;
import it.petrillo.jbomberman.util.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The GameCharacter abstract class represents a character entity in the game.
 * It extends the GameEntity class and implements the Collidable interface.
 * Subclasses of GameCharacter define specific characters with behavior and properties.
 */
public abstract class GameCharacter extends GameEntity implements Collidable {

    protected CollisionListener collisionListener;
    protected Direction movingDirection;
    protected boolean movingUp, movingDown, movingLeft, movingRight, hitted;
    protected int health, xSpeed, ySpeed, characterSpeed, hittedTimer, defaultSpriteWidth,defaultSpriteHeight;

    /**
     * Constructs a GameCharacter with the specified initial position.
     *
     * @param x The X-coordinate of the character's initial position.
     * @param y The Y-coordinate of the character's initial position.
     */
    public GameCharacter(int x, int y) {
        super(x, y);
    }

    /**
     * Determines the animation index based on the character's moving direction.
     *
     * @return The animation index corresponding to the character's moving direction.
     */
    protected int getAniIndexByDirection() {
        if (spriteAnimation.length <= 1)
            return 0;
        switch (movingDirection) {
            case UP -> {
                return 0;
            }
            case DOWN -> {
                return 2;
            }
            case LEFT -> {
                return 3;
            }
            case RIGHT -> {
                return 1;
            }
        }
        return 2;
    }

    /**
     * Sets the CollisionListener for the character.
     *
     * @param collisionListener The CollisionListener to associate with the character.
     */
    public void setCollisionListener(CollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    // Getter and setter methods for moving direction and movement flags

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    protected void drawFlashingSprite(Graphics2D g, BufferedImage img) {
        g.drawImage(img, x, y, (int) (defaultSpriteWidth * entityScale), (int) (defaultSpriteHeight * entityScale), null);
        if (hittedTimer % 3 == 0) {
            BufferedImage flashImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int row = 0; row < img.getHeight(); row++) {
                for (int col = 0; col < img.getWidth(); col++) {
                    int originalPixel = img.getRGB(col, row);
                    // 24 bit right-shift + bitwise AND with 255 (0xFF) to obtain the alpha value of the pixel
                    int alpha = (originalPixel >> 24) & 0xFF;
                    // if not complete transparent (alpha == 255) the pixel will be converted to white pixel
                    int newPixel = (alpha == 255) ? Color.WHITE.getRGB() : originalPixel;
                    flashImage.setRGB(col, row, newPixel);
                }
            }
            g.drawImage(flashImage, x, y, (int) (defaultSpriteWidth * entityScale), (int) (defaultSpriteHeight * entityScale), null);
        }
    }


    /**
     * Defines the behavior when the character is hit by a collision.
     * Subclasses should implement this method to handle character reactions.
     */
    public abstract void hitCharacter();
}
