package it.petrillo.jbomberman.model;

import java.awt.*;

import static it.petrillo.jbomberman.util.GameSettings.TILE_SIZE;

public abstract class PowerUp extends GameObject implements Collidable {

    public PowerUp(int x, int y) {
        super(x, y);
        visible = false;
        animationSpeed = 5;
    }
    public abstract void applyEffect(Bomberman player);

    @Override
    public void update() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 2)
                animationIndex = 0;
        }
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Bomberman) {
            applyEffect((Bomberman)other);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            g.drawImage(spriteAnimation[0][animationIndex],x,y,TILE_SIZE,TILE_SIZE,null);
        }
    }
}
