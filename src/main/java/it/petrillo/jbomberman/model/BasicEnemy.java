package it.petrillo.jbomberman.model;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameSettings.*;

public class BasicEnemy extends Enemy {

    public BasicEnemy(int x, int y) {
        super(x, (int) (y-8*SCALE));
        entityScale = 3.5d;
        characterSpeed = 2;
        yCollisionOffset = (int)(16*entityScale);
        xCollisionOffset = (int)(4*entityScale);
        collisionBox.setLocation(super.x+xCollisionOffset,super.y+yCollisionOffset);
        collisionBox.setSize((int)(10*entityScale), (int) (9*entityScale));
        health = 1;
        animationSpeed = 5;
        movingDirection = pickRandomDirection();
        loadSprites( "/Sprites_Enemy1_16x24.png");
    }


    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            if (hittedTimer > 0){
                BufferedImage img = spriteAnimation[getAniIndexByDirection()][animationIndex];
                g.drawImage(img, x, y, (int) (16*entityScale),(int)(24*entityScale), null);
                if (hittedTimer%3 == 0) {
                    int width = img.getWidth();
                    int height = img.getHeight();
                    BufferedImage filteredImg = new BufferedImage(width,height,img.getType());
                    Graphics2D g2d = filteredImg.createGraphics();
                    g2d.drawImage(img,0,0,null);
                    g2d.dispose();
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            int rgb = filteredImg.getRGB(x, y);
                            int alpha = (rgb >> 24) & 0xFF;
                            if (alpha > 0) {
                                Color color = new Color(rgb);
                                Color white = new Color(255, 255, 255, alpha);
                                filteredImg.setRGB(x, y, white.getRGB());
                            }
                        }
                    }
                    g.drawImage(filteredImg, x, y, (int) (16*entityScale),(int)(24*entityScale), null);
                }
            }
            else
                g.drawImage(spriteAnimation[getAniIndexByDirection()][animationIndex],x,y,
                    (int) (16*entityScale),(int)(24*entityScale), null);
        }
    }

    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[4][4];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j,24*i,16,24);
            }
        }
    }

    @Override
    public void update() {
        if (health > 0) {
            updatePosition();
            animationTick++;
            if (animationTick >= animationSpeed) {
                animationTick = 0;
                animationIndex++;
                if (animationIndex >= 4)
                    animationIndex = 0;
            }
        }
        else {
            hittedTimer--;
            if (hittedTimer <= 0) {
                setToClean(true);
                visible = false;
            }
        }
    }

}
