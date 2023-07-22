package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.GameUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class SoftBlock extends GameObject {

    private boolean hasShadow;
    public SoftBlock(int x, int y, boolean visible) {
        super(x, y, visible);
        isDestroyable = true;
        isSolid = true;
        animationSpeed = 10;
        loadSprites("/Sprites_softBlock_16x16.png");
    }

    @Override
    public void draw(Graphics2D g) {
        int rowIndex = 0;
        if(hasShadow)
            rowIndex = 1;
        g.drawImage(spriteAnimation[rowIndex][animationIndex],x,y, TILE_SIZE, TILE_SIZE, null);
    }

    @Override
    protected void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[2][4];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,16*i, 16, 16);
            }
        }
    }

    @Override
    public void updateStatus() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 4)
                animationIndex = 0;
        }
    }

    public void setHasShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
    }

}
