package it.petrillo.jbomberman.model.objects;

import it.petrillo.jbomberman.model.interfaces.Collidable;
import it.petrillo.jbomberman.model.characters.Bomberman;

import java.awt.*;

import static it.petrillo.jbomberman.util.GameConstants.TILE_SIZE;

/**
 * The PowerUp class represents a game entity that provides a special effect or enhancement to the player's character.
 * PowerUp objects can be collected by the player's character upon collision.
 */
public abstract class PowerUp extends GameObject implements Collidable {

    /**
     * Constructs a PowerUp object with the specified position.
     *
     * @param x The x-coordinate of the power-up's position.
     * @param y The y-coordinate of the power-up's position.
     */
    public PowerUp(int x, int y) {
        super(x, y);
        visible = false;
        animationSpeed = 5;
    }

    /**
     * Applies the effect of the power-up to the specified Bomberman player character.
     *
     * @param player The Bomberman player character to apply the power-up effect to.
     */
    public abstract void applyEffect(Bomberman player);

    /**
     * Updates the animation state of the power-up. This method is called in each game frame.
     * It advances the animation to the next frame.
     */
    @Override
    public void update() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 2)
                animationIndex = 0;
        }
    }

    /**
     * Handles collision events with other Collidable objects. If the collision is with a Bomberman player,
     * this method triggers the power-up effect by calling applyEffect on the player.
     *
     * @param other The Collidable object that this power-up collides with.
     */
    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Bomberman) {
            applyEffect((Bomberman)other);
        }
    }

    /**
     * Renders the power-up by drawing its current animation frame on the game canvas.
     *
     * @param g The Graphics2D context used for drawing.
     */
    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            g.drawImage(spriteAnimation[0][animationIndex],x,y,TILE_SIZE,TILE_SIZE,null);
        }
    }
}
