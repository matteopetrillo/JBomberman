package it.petrillo.jbomberman.model;

import java.awt.image.BufferedImage;
import static it.petrillo.jbomberman.util.GameSettings.*;

/**
 * The LifeAdder class represents a specific type of power-up that allows the player to gain an additional life.
 * It extends the PowerUp class and provides methods to load its sprite and apply its effect to the player.
 */
public class LifeAdder extends PowerUp {

    /**
     * Creates a new LifeAdder instance with the specified initial coordinates.
     *
     * @param x The initial x-coordinate of the life adder.
     * @param y The initial y-coordinate of the life adder.
     */
    public LifeAdder(int x, int y) {
        super(x, y);
        loadSprites("/Sprite_Items_16x16.png");
    }

    /**
     * Loads the sprite images for the life adder from the specified path.
     *
     * @param path The path to the sprite sheet image.
     */
    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[1][2];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,16*2,16,16);
            }
        }
    }

    /**
     * Applies the effect of the life adder to the specified Bomberman player.
     * This method increases the player's health by one, effectively granting an additional life.
     *
     * @param player The Bomberman player to apply the effect to.
     */
    @Override
    public void applyEffect(Bomberman player) {
        player.healCharacter();
        visible = false;
        setToClean(true);
    }

}
