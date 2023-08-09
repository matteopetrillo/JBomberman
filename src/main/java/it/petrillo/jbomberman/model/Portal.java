package it.petrillo.jbomberman.model;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameUtils.TILE_SIZE;
import static it.petrillo.jbomberman.util.GameUtils.getImg;

public class Portal extends PowerUp {

    public Portal(int x, int y) {
        super(x, y);
    }

    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[1][2];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,0,16,16);
            }
        }
    }

    @Override
    public void applyEffect(Bomberman player) {

    }
}
