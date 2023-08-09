package it.petrillo.jbomberman.model;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class Bomb extends GameObject implements Explodable {
    private int timer = 80;
    private boolean explosionStarted;
    private boolean isExploding;

    public Bomb(int x, int y, String spriteSheetPath) {
        super(x, y);
        isSolid = false;
        animationSpeed = 15;
        visible = true;
        loadSprites(spriteSheetPath);
    }

    @Override
    public void draw(Graphics2D g) {
        if (isVisible()) {
            if (isExploding)
                g.drawImage(spriteAnimation[1][animationIndex], x, y, TILE_SIZE, TILE_SIZE, null);
            else
                g.drawImage(spriteAnimation[0][animationIndex], x, y, TILE_SIZE, TILE_SIZE, null);
        }
    }

    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        int rows = spriteSheet.getHeight()/DEFAULT_TILE_SIZE;
        int height = spriteSheet.getWidth()/DEFAULT_TILE_SIZE;
        spriteAnimation = new BufferedImage[rows][height];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,16*i,16,16);
            }
        }
    }

    @Override
    public void update() {
        if(timer > 0) {
            timer--;
            if (timer <= 0) {
                isExploding = true;
                animationSpeed = 5;
            }
        }
        updateAnimation();
    }

    public void updateAnimation() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 5)
                if (!isExploding)
                    animationIndex = 0;
                else {
                    setToClean(true);
                    visible = false;
                }
        }
    }

    public boolean isExplosionStarted() {
        return explosionStarted;
    }

    public void setExplosionStarted(boolean explosionStarted) {
        this.explosionStarted = explosionStarted;
    }

    @Override
    public void setExploding(boolean exploding) {
        this.isExploding = exploding;
    }

    @Override
    public boolean isExploding() {
        return this.isExploding;
    }

}
