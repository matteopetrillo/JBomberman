package it.petrillo.jbomberman.model.objects;

import it.petrillo.jbomberman.model.characters.Bomberman;

import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.UtilFunctions.getImg;


/**
 * The BombAdder class represents a power-up that adds bombs to the player's backpack.
 * It extends the PowerUp class.
 */
public class BombAdder extends PowerUp {

    /**
     * Constructs a BombAdder instance with the specified initial position.
     *
     * @param x The X-coordinate of the power-up's initial position.
     * @param y The Y-coordinate of the power-up's initial position.
     */
    public BombAdder(int x, int y) {
        super(x, y);
        loadSprites("/Sprite_Items_16x16.png");
    }

    /**
     * Loads the sprite animation frames for the BombAdder power-up from the specified sprite sheet path.
     *
     * @param path The path to the sprite sheet file.
     */
    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[1][2];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                if (spriteSheet != null) {
                    spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,16,16,16);
                }
            }
        }
    }


    /**
     * Applies the effect of the BombAdder power-up to the player.
     * Increases the player's bomb backpack capacity and marks the power-up for cleanup.
     *
     * @param player The Bomberman player to apply the effect to.
     */
    @Override
    public void applyEffect(Bomberman player) {
        player.increaseBombBackpack();
        visible = false;
        setToClean(true);
    }
}
