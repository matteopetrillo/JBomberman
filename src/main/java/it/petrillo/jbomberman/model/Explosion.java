package it.petrillo.jbomberman.model;



import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class Explosion extends GameEntity {

    private ArrayList<Direction> directions;
    public Explosion(int x, int y, String spritesPath, ArrayList<Direction> directions) {
        super(x, y);
        this.directions = directions;
        animationSpeed = 7;
        visible = true;
        loadSprites(spritesPath);
    }

    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            if (directions.contains(Direction.UP))
                g.drawImage(spriteAnimation[1][animationIndex],x,y-TILE_SIZE,TILE_SIZE,TILE_SIZE,null);
            if (directions.contains(Direction.DOWN))
                g.drawImage(spriteAnimation[2][animationIndex],x,y+TILE_SIZE,TILE_SIZE,TILE_SIZE,null);
            if (directions.contains(Direction.RIGHT))
                g.drawImage(spriteAnimation[3][animationIndex],x+TILE_SIZE,y,TILE_SIZE,TILE_SIZE,null);
            if (directions.contains(Direction.LEFT))
                g.drawImage(spriteAnimation[4][animationIndex],x-TILE_SIZE,y,TILE_SIZE,TILE_SIZE,null);
            g.drawImage(spriteAnimation[0][animationIndex],x,y,TILE_SIZE,TILE_SIZE,null);
        }
    }

    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[5][5];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j, 16*i,16,16);
            }
        }
    }

    @Override
    public void update() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 5)
                visible = false;
        }
    }

}
