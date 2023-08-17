package it.petrillo.jbomberman.model;


import it.petrillo.jbomberman.controller.AudioManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import static it.petrillo.jbomberman.util.GameSettings.*;

/**
 * The Enemy abstract class serves as a base for enemy characters in the game.
 * It extends the GameCharacter class and implements the Movable and Renderable interfaces.
 * This class encapsulates common behavior and attributes for enemy characters.
 */
public abstract class Enemy extends GameCharacter implements Movable, Renderable {

    int scoreTextY, scoreValue;

    /**
     * Constructs an Enemy instance with the specified initial position.
     *
     * @param x The X-coordinate of the enemy's initial position.
     * @param y The Y-coordinate of the enemy's initial position.
     */
    public Enemy(int x, int y) {
        super(x, y);
        movingDirection = pickRandomDirection();
        visible = true;
    }

    /**
     * Updates the state of the BasicEnemy character.
     * Handles movement, animation, and visibility.
     */
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
                if (animationIndex >= (spriteAnimation[getAniIndexByDirection()].length-1))
                    animationIndex = 0;
            }
        }
        else {
            if (hittedTimer <= 0) {
                visible = false;
                if (scoreTextY > 20)
                    scoreTextY--;
                Timer cleanTimer = new Timer(500, e -> {
                    setToClean(true);
                });
                cleanTimer.setRepeats(false);
                cleanTimer.start();
            }
        }
    }


    /**
     * Picks a random movement direction for the enemy.
     *
     * @return A randomly chosen Direction for movement.
     */
    protected Direction pickRandomDirection() {
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
        }
        return null;
    }

    /**
     * Updates the position of the enemy based on its current direction and collision conditions.
     */
    @Override
    public void updatePosition() {
        setFlagFromDirection();
        int[] deltaSpeed = getDeltaSpeedByDirection();
        xSpeed = deltaSpeed[0];
        ySpeed = deltaSpeed[1];

        Area newCollisionBox = new Area(collisionBox);
        AffineTransform transform = AffineTransform.getTranslateInstance(xSpeed, ySpeed);
        newCollisionBox.transform(transform);

        if(!collisionListener.canMoveThere(xSpeed, ySpeed,collisionBox)) {
            changeDirection();
            int[] deltas = getDeltaSpeedByDirection();
            xSpeed = deltas[0];
            ySpeed = deltas[1];
        }
        super.x += xSpeed;
        super.y += ySpeed;
        collisionBox = newCollisionBox;
    }

    /**
     * Draws the enemy character on the provided graphics.
     * Handles animation and the hit effect.
     *
     * @param g The Graphics2D object to draw on.
     */
    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            BufferedImage img = spriteAnimation[getAniIndexByDirection()][animationIndex];
            if (hittedTimer > 0) {
                g.drawImage(img, x, y, (int) (defaultSpriteWidth * entityScale), (int) (defaultSpriteHeight * entityScale), null);
                if (hittedTimer % 3 == 0) {
                    BufferedImage flashImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    for (int row = 0; row < img.getHeight(); row++) {
                        for (int col = 0; col < img.getWidth(); col++) {
                            int originalPixel = img.getRGB(col, row);
                            int alpha = (originalPixel >> 24) & 0xFF;
                            int newPixel = (alpha == 255) ? Color.WHITE.getRGB() : originalPixel;
                            flashImage.setRGB(col, row, newPixel);
                        }
                    }
                    g.drawImage(flashImage, x, y, (int) (defaultSpriteWidth * entityScale), (int) (defaultSpriteHeight * entityScale), null);
                }
            }

            else {
                g.drawImage(img, x, y, (int) (defaultSpriteWidth * entityScale), (int) (defaultSpriteHeight * entityScale), null);
            }
        }

        else {
            g.setFont(RETRO_FONT.deriveFont(Font.PLAIN,50f));
            drawBorder(g, x, scoreTextY, 4, String.valueOf(scoreValue));
            g.setColor(Color.ORANGE);
            g.drawString(String.valueOf(scoreValue), x, scoreTextY);
        }
    }


    /**
     * Changes the enemy's direction to an available direction other than the current one.
     */
    private void changeDirection() {
        List<Direction> availableDirections = collisionListener.getAvailableDirections(characterSpeed,collisionBox);
        Random rd = new Random();
        try {
            int n = rd.nextInt(0, availableDirections.size());
            movingDirection = availableDirections.get(n);
        } catch (IllegalArgumentException e) {
            invertDirection(movingDirection);
        }
        setFlagFromDirection();
    }

    /**
     * Calculates the change in speed based on the current movement direction.
     *
     * @return An array containing the change in X-speed and Y-speed.
     */
    private int[] getDeltaSpeedByDirection() {
        int[] deltaSpeed = new int[2];
        xSpeed = 0;
        ySpeed = 0;

        if (movingUp) {
            ySpeed = -characterSpeed;
        }
        else if (movingDown) {
            ySpeed = characterSpeed;
        }
        else if (movingLeft) {
            xSpeed = -characterSpeed;
        }
        else if (movingRight) {
            xSpeed = characterSpeed;
        }
        deltaSpeed[0] = xSpeed;
        deltaSpeed[1] = ySpeed;

        return deltaSpeed;
    }

    /**
     * Inverts the enemy's direction, used for collision handling.
     *
     * @param direction The current direction to be inverted.
     */
    private void invertDirection(Direction direction) {
        switch (direction) {
            case UP -> {
                movingDirection = Direction.DOWN;
            }
            case DOWN -> {
                movingDirection = Direction.UP;
            }
            case LEFT -> {
                movingDirection = Direction.RIGHT;
            }
            case RIGHT -> {
                movingDirection = Direction.LEFT;
            }
        }
        setFlagFromDirection();
    }

    /**
     * Sets the movement flags based on the current movement direction.
     */
    private void setFlagFromDirection() {
        movingLeft = false;
        movingRight = false;
        movingDown = false;
        movingUp = false;

        switch (movingDirection) {
            case UP -> {
                movingUp = true;
            }
            case DOWN -> {
                movingDown = true;
            }
            case LEFT -> {
                movingLeft = true;
            }
            case RIGHT -> {
                movingRight = true;
            }
        }
    }

    /**
     * Handles the effect of being hit by a collision or a bomb.
     * Decreases the enemy's health and manages the hit effect timer.
     */
    @Override
    public void hitCharacter() {
        if (hittedTimer <= 0) {
            health--;
            hittedTimer = 60;
            hitted = false;
        }
    }

    public int getScoreValue() {
        return scoreValue;
    }

    /**
     * Handles collision events with other Collidable objects.
     * Changes direction when colliding with the player and inverts direction when colliding with another enemy.
     */
    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Bomberman)
            changeDirection();
        if (other instanceof Enemy)
            invertDirection(movingDirection);
    }
}
