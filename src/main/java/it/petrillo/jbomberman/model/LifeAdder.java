package it.petrillo.jbomberman.model;

import java.awt.image.BufferedImage;
import static it.petrillo.jbomberman.util.GameSettings.*;

public class LifeAdder extends PowerUp {
    public LifeAdder(int x, int y) {
        super(x, y);
        loadSprites("/Sprite_Items_16x16.png");
    }

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
    @Override
    public void applyEffect(Bomberman player) {
        player.healCharacter();
        visible = false;
        setToClean(true);
    }

}
