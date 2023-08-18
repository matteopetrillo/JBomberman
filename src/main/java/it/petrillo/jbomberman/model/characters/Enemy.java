package it.petrillo.jbomberman.model.characters;


import it.petrillo.jbomberman.model.interfaces.Collidable;
import it.petrillo.jbomberman.util.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import static it.petrillo.jbomberman.util.GameConstants.*;
import static it.petrillo.jbomberman.util.UtilFunctions.drawBorder;

/**
 * The Enemy abstract class serves as a base for enemy characters in the game.
 * It extends the GameCharacter class.
 * This class encapsulates common behavior and attributes for enemy characters.
 */
public abstract class Enemy extends GameCharacter {

    protected int scoreTextYOffset, scoreValue;

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
        if (armorTimer > 0)
            armorTimer--;
        if (health > 0) {
            updatePosition();
            animationTick++;
            scoreTextYOffset = y;
            if (animationTick >= animationSpeed) {
                animationTick = 0;
                animationIndex++;
                if (animationIndex >= (spriteAnimation[getAnimationIndexByDirection()].length-1))
                    animationIndex = 0;
            }
        }
        else {
            if (armorTimer <= 0) {
                visible = false;
                if (scoreTextYOffset > 20)
                    scoreTextYOffset--;
                Timer cleanTimer = new Timer(500, e -> setToClean(true));
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
        Random random = new Random();
        int n = random.nextInt(1,4);
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
            default -> throw new IllegalStateException("Unexpected value: " + n);
        }
    }

    /**
     * Updates the position of the enemy based on its current direction and collision conditions.
     */
    @Override
    public void updatePosition() {
        setMovementFlag();
        int[] deltaSpeed = getDeltasByDirection();
        xSpeed = deltaSpeed[0];
        ySpeed = deltaSpeed[1];

        Area newCollisionBox = new Area(collisionBox);
        AffineTransform transform = AffineTransform.getTranslateInstance(xSpeed, ySpeed);
        newCollisionBox.transform(transform);

        if(!collisionListener.canMoveThere(xSpeed, ySpeed,collisionBox)) {
            changeDirection();
            int[] deltas = getDeltasByDirection();
            xSpeed = deltas[0];
            ySpeed = deltas[1];
        }
        x += xSpeed;
        y += ySpeed;
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
            BufferedImage img = spriteAnimation[getAnimationIndexByDirection()][animationIndex];
            if (armorTimer > 0) {
                drawFlashingSprite(g,img);
            }
            else {
                g.drawImage(img, x, y, (int) (defaultSpriteWidth * entityScale), (int) (defaultSpriteHeight * entityScale), null);
            }
        }
        else {
            g.setFont(RETRO_FONT.deriveFont(Font.PLAIN,50f));
            drawBorder(g, x, scoreTextYOffset, 4, String.valueOf(scoreValue));
            g.setColor(Color.ORANGE);
            g.drawString(String.valueOf(scoreValue), x, scoreTextYOffset);
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
            invertDirection();
        }
        setMovementFlag();
    }

    /**
     * Calculates the change in speed based on the current movement direction.
     *
     * @return An array containing the change in X-speed and Y-speed.
     */
    private int[] getDeltasByDirection() {
        int[] deltaSpeeds = new int[2];
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
        deltaSpeeds[0] = xSpeed;
        deltaSpeeds[1] = ySpeed;

        return deltaSpeeds;
    }

    /**
     * Inverts the enemy's direction, used for collision handling.
     *
     */
    private void invertDirection() {
        switch (movingDirection) {
            case UP -> movingDirection = Direction.DOWN;
            case DOWN -> movingDirection = Direction.UP;
            case LEFT -> movingDirection = Direction.RIGHT;
            case RIGHT -> movingDirection = Direction.LEFT;
        }
        setMovementFlag();
    }

    /**
     * Sets the movement flags based on the current movement direction.
     */
    private void setMovementFlag() {
        movingLeft = false;
        movingRight = false;
        movingDown = false;
        movingUp = false;

        switch (movingDirection) {
            case UP -> movingUp = true;
            case DOWN -> movingDown = true;
            case LEFT -> movingLeft = true;
            case RIGHT -> movingRight = true;
        }
    }

    /**
     * Handles the effect of being hit by a collision or a bomb.
     * Decreases the enemy's health and manages the hit effect timer.
     */
    @Override
    public void hitCharacter() {
        if (armorTimer <= 0) {
            health--;
            armorTimer = 60;
        }
    }

    /**
     * Returns the score values of the enemy, increased by difficult to defeat it.
     * @return Enemy score value
     */
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
        else if (other instanceof Enemy)
            invertDirection();
    }
}
