package it.petrillo.jbomberman.model.objects;

import it.petrillo.jbomberman.model.characters.Bomberman;

import java.awt.image.BufferedImage;
import static it.petrillo.jbomberman.util.UtilFunctions.getImg;

/**
 * The ScoreAdder class represents a power-up that increases the player's score upon collection.
 * This power-up is rendered as a sprite image on the game canvas and provides an effect to the player.
 */
public class ScoreAdder extends PowerUp {

    /**
     * Creates a new ScoreAdder object at the specified coordinates.
     *
     * @param x The x-coordinate of the power-up's position.
     * @param y The y-coordinate of the power-up's position.
     */
    public ScoreAdder(int x, int y) {
        super(x, y);
        loadSprites("/Sprites.Objects/Sprite_Items_16x16.png");
    }

    /**
     * Loads the sprite images for the ScoreAdder power-up from the specified path.
     *
     * @param path The path to the sprite image resource.
     */
    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[1][2];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                if (spriteSheet != null) {
                    spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,16*3,16,16);
                }
            }
        }
    }

    /**
     * Applies the effect of the ScoreAdder power-up to Bomberman.
     * Increases the player's score by a predefined value upon collection.
     *
     * @param player The Bomberman player who collects the power-up.
     */
    @Override
    public void applyEffect(Bomberman player) {
        player.alterScore(100);
        visible = false;
        setToClean(true);
    }
}
