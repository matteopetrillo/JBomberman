package it.petrillo.jbomberman.model.objects;

import it.petrillo.jbomberman.model.interfaces.Explodable;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameConstants.*;
import static it.petrillo.jbomberman.util.UtilFunctions.getImg;

/**
 * The SoftBlock class represents a destructible block that can be destroyed by explosions.
 * They are rendered as sprite images on the game canvas.
 */
public class SoftBlock extends GameObject implements Explodable {

    private boolean hasShadow;
    private boolean isExploding;

    /**
     * Creates a new SoftBlock object at the specified coordinates using the provided sprite sheet.
     *
     * @param x         The x-coordinate of the SoftBlock's position.
     * @param y         The y-coordinate of the SoftBlock's position.
     * @param sheetPath The path to the sprite sheet resource for rendering.
     */
    public SoftBlock(int x, int y, String sheetPath, boolean hasShadow) {
        super(x, y);
        isDestroyable = true;
        isSolid = true;
        animationSpeed = 8;
        visible = true;
        this.hasShadow = hasShadow;
        loadSprites(sheetPath);
    }

    /**
     * Draws the SoftBlock on the game canvas using the Graphics2D context.
     *
     * @param g The Graphics2D context used for rendering.
     */
    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            int rowIndex = 0;
            if (!isExploding) {
                if (hasShadow)
                    rowIndex = 1;
                g.drawImage(spriteAnimation[rowIndex][animationIndex], x, y, TILE_SIZE, TILE_SIZE, null);
            }
            else {
                rowIndex = spriteAnimation.length-1;
                g.drawImage(spriteAnimation[rowIndex][animationIndex], x, y, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }

    /**
     * Loads the sprite images for the SoftBlock from the specified path.
     *
     * @param path The path to the sprite image resource.
     */
    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        int rows = spriteSheet.getHeight()/DEFAULT_TILE_SIZE;
        spriteAnimation = new BufferedImage[rows][];
        for (int i = 0; i < spriteAnimation.length; i++) {
            if (i != spriteAnimation.length-1)
                spriteAnimation[i] = new BufferedImage[4];
            spriteAnimation[i] = new BufferedImage[5];
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,16*i, 16, 16);
            }
        }
    }

    /**
     * Updates the animation state of the SoftBlock.
     */
    @Override
    public void update() {
        int maxIndex = 4;
        if (isExploding)
            maxIndex = 5;
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= maxIndex)
                if (isExploding) {
                    visible = false;
                    this.setToClean(true);
                }
                else
                    animationIndex = 0;
        }
    }

    /**
     * Sets whether the SoftBlock is under a wall shadow.
     *
     * @param hasShadow True if the SoftBlock should have a wall shadow, false otherwise.
     */
    public void setHasShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
    }

    /**
     * Sets the explosion state of the SoftBlock.
     *
     * @param exploding True if the SoftBlock is currently exploding, false otherwise.
     */
    @Override
    public void setExploding(boolean exploding) {
        isExploding = exploding;
    }

    /**
     * Checks if the SoftBlock is currently exploding.
     *
     * @return True if the SoftBlock is exploding, false otherwise.
     */
    @Override
    public boolean isExploding() {
        return isExploding;
    }

}
