package it.petrillo.jbomberman.model;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameSettings.*;

public class SoftBlock extends GameObject implements Explodable {

    private boolean hasShadow;
    private boolean isExploding;
    public SoftBlock(int x, int y, String sheetPath) {
        super(x, y);
        isDestroyable = true;
        isSolid = true;
        animationSpeed = 8;
        visible = true;
        loadSprites(sheetPath);
    }

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

    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        int rows = spriteSheet.getHeight()/DEFAULT_TILE_SIZE;
        spriteAnimation = new BufferedImage[rows][];
        for (int i = 0; i < spriteAnimation.length; i++) {
            if (i != spriteAnimation.length-1)
                spriteAnimation[i] = new BufferedImage[4];
            spriteAnimation[i] = new BufferedImage[6];
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,16*i, 16, 16);
            }
        }
    }

    @Override
    public void update() {
        int maxIndex = 4;
        if (isExploding)
            maxIndex = 6;
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

    public void setHasShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
    }

    @Override
    public void setExploding(boolean exploding) {
        isExploding = exploding;
    }

    @Override
    public boolean isExploding() {
        return isExploding;
    }

}
