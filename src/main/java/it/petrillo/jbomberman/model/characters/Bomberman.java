package it.petrillo.jbomberman.model.characters;

import it.petrillo.jbomberman.model.interfaces.Collidable;
import it.petrillo.jbomberman.util.Direction;
import it.petrillo.jbomberman.util.NotificationType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import static it.petrillo.jbomberman.util.GameConstants.*;
import static it.petrillo.jbomberman.util.UtilFunctions.*;

/**
 * The Bomberman class represents the player character in the game.
 * It extends the abstract class GameCharacter.
 * It manages player-specific information and behavior.
 */
public class Bomberman extends GameCharacter {

    private static Bomberman bombermanInstance;
    private int bombBackpack,bombReleased, score;

    /**
     * Private constructor for the Bomberman singleton.
     * Initializes the player's characteristics such as speed, position, health, and more.
     */
    private Bomberman() {
        super(0, 0);
        entityScale = 3.5d;
        xCollisionOffset = (int) (4*entityScale);
        yCollisionOffset = (int) (7*entityScale);
        animationSpeed = 13;
        defaultSpriteHeight = 32;
        defaultSpriteWidth = 32;
        initDefaultValues();
        loadSprites("/Sprites/Bomberman/Sprites_Bomberman.png");
    }

    private void initDefaultValues() {
        score = 0;
        health = 5;
        characterSpeed = 4;
        bombBackpack = 1;
        armorTimer = 80;
        movingDirection = Direction.DOWN;
        visible = true;
    }

    /**
     * Draws the Bomberman character on the provided graphics.
     * Also manages death animation and the hit effect.
     *
     * @param g The Graphics2D object to draw on.
     */
    @Override
    public void draw(Graphics2D g) {
        if(visible) {
            if (health <= 0)
                g.drawImage(spriteAnimation[4][animationIndex],
                        x, y, (int) (defaultSpriteWidth * entityScale), (int) (defaultSpriteHeight * entityScale), null);
            else if (armorTimer > 0){
                BufferedImage img = spriteAnimation[getAnimationIndexByDirection()][animationIndex];
                drawFlashingSprite(g,img);
            }
            else {
                g.drawImage(spriteAnimation[getAnimationIndexByDirection()][animationIndex],
                        x, y, (int) (defaultSpriteWidth * entityScale), (int) (defaultSpriteHeight * entityScale), null);
            }
        }
    }

    /**
     * Loads the character's sprites from a specified path.
     *
     * @param path The path to the sprite file.
     */
    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[5][3];

        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                if (spriteSheet != null)
                    spriteAnimation[i][j] = spriteSheet.getSubimage(defaultSpriteWidth * j, defaultSpriteHeight * i,
                            defaultSpriteWidth, defaultSpriteHeight);
            }
        }
    }

    /**
     * Sets the X position of the character with an appropriate offset.
     *
     * @param x The X position to set.
     */
    @Override
    public void setX(int x) {
        super.setX((int) (x-6*SCALE));
    }

    /**
     * Sets the Y position of the character with an appropriate offset.
     *
     * @param y The Y position to set.
     */
    @Override
    public void setY(int y) {
        super.setY((int) (y-14*SCALE));
    }


    /**
     * Sets the position of the character to the specified coordinates,
     * updating also the collision box.
     *
     * @param x The X-coordinate to set the character's position to.
     * @param y The Y-coordinate to set the character's position to.
     */
    public void setPosition(int x, int y) {
        super.setX((int) (x-6*SCALE));
        super.setY((int) (y-14*SCALE));
        collisionBox = new Area(new Rectangle(x+xCollisionOffset,y+yCollisionOffset,
                (int) (10*entityScale), (int) (6*entityScale)));
    }

    /**
     * Returns the X position of the character's hitbox.
     *
     * @return The X position of the character's hitbox.
     */
    @Override
    public int getX() {
        return (int) collisionBox.getBounds().getX();
    }

    /**
     * Returns the Y position of the character's hitbox.
     *
     * @return The Y position of the character's hitbox.
     */
    @Override
    public int getY() {
        return (int) collisionBox.getBounds().getY();
    }

    /**
     * Updates the character's state, handling movement and animation.
     */
    @Override
    public void update() {
        if (armorTimer > 0)
            armorTimer--;
        if (health > 0)
            updatePosition();
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            if (health <= 0) {
                animationIndex++;
                if (animationIndex >= (spriteAnimation[getAnimationIndexByDirection()].length-1)) {
                    visible = false;
                    notifyObservers(NotificationType.GAME_OVER,null);
                }
            }
            else if (movingDown || movingLeft || movingUp || movingRight) {
                animationIndex++;
                if (animationIndex >= (spriteAnimation[getAnimationIndexByDirection()].length-1))
                    animationIndex = 0;
            }
            else
                animationIndex = 0;
        }
    }

    /**
     * Updates the character's state, handling movement and animation.
     */
    @Override
    public void updatePosition() {
        xSpeed = 0;
        ySpeed = 0;

        if (movingUp) {
            ySpeed = -characterSpeed;
            movingDirection = Direction.UP;
        }
        else if (movingDown) {
            ySpeed = characterSpeed;
            movingDirection = Direction.DOWN;
        }
        else if (movingLeft) {
            xSpeed = -characterSpeed;
            movingDirection = Direction.LEFT;
        }
        else if (movingRight) {
            xSpeed = characterSpeed;
            movingDirection = Direction.RIGHT;
        }

        Area newCollisionBox = new Area(collisionBox);
        AffineTransform transform = AffineTransform.getTranslateInstance(xSpeed, ySpeed);
        newCollisionBox.transform(transform);

        if (collisionListener.canMoveThere(xSpeed, ySpeed,collisionBox)) {
            x += xSpeed;
            y += ySpeed;
            collisionBox = newCollisionBox;
        }
    }

    /**
     * Handles collision events between the player and other Collidable objects.
     *
     * @param other The other Collidable object involved in the collision.
     */
    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy && ((Enemy)other).isVisible())
            hitCharacter();
    }

    /**
     * Increases the player's health by one.
     */
    public void increaseHealthCharacter() {
        health++;
        notifyObservers(NotificationType.HEALTH_UPDATE,health);
    }

    /**
     * Increases the player's bomb backpack capacity by one and notifies observers.
     */
    public void increaseBombBackpack() {
        bombBackpack++;
        notifyObservers(NotificationType.BOMB_UPDATE, getAvailableBombs());
    }

    /**
     * Returns the current number of available bombs the player can drop.
     *
     * @return The number of available bombs.
     */
    public int getAvailableBombs() {
        return bombBackpack-bombReleased;
    }

    /**
     * Handles the action of being hit by a collision or attack.
     * Reduces the character's health and manages the hit timer.
     * If health drops to zero or below, triggers the death animation.
     */
    @Override
    public void hitCharacter() {
        if (armorTimer <= 0 && health > 0) {
            health--;
            armorTimer = 60;
            notifyObservers(NotificationType.HEALTH_UPDATE,health);
        }
        else if (health <= 0) {
            animationIndex = 0;
            animationSpeed = 18;
        }
    }

    /**
     * Reset player's stat to default and notify it to observers.
     */
    public void resetPlayer() {
        initDefaultValues();
        notifyObservers(NotificationType.HEALTH_UPDATE,health);
        notifyObservers(NotificationType.SCORE_UPDATE, score);
        notifyObservers(NotificationType.BOMB_UPDATE, getAvailableBombs());
    }

    /**
     * Advances the game level by notifying observers that the current level has finished.
     */
    public void advanceLevel() {
        notifyObservers(NotificationType.FINISH_LEVEL,null);
    }

    /**
     * Checks if the player can drop a bomb.
     *
     * @return True if the player can drop a bomb, otherwise false.
     */
    public boolean canDropBomb() {
        return getAvailableBombs()>0;
    }

    /**
     * Increments the bomb release counter.
     *
     * @param k The number of released bombs to add to the counter.
     */
    public void alterBombReleased(int k) {
        bombReleased += k;
        notifyObservers(NotificationType.BOMB_UPDATE, getAvailableBombs());
    }

    /**
     * Increments the player's score.
     *
     * @param k The score to add to the current score.
     */
    public void alterScore(int k) {
        score += k;
        notifyObservers(NotificationType.SCORE_UPDATE,score);
    }

    /**
     * Returns a singleton instance of the Bomberman character.
     *
     * @return The singleton instance of the Bomberman character.
     */
    public static Bomberman getPlayerInstance() {
        if(bombermanInstance == null) {
            bombermanInstance = new Bomberman();
        }
        return bombermanInstance;
    }
}
