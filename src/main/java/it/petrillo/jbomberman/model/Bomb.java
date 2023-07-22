package it.petrillo.jbomberman.model;

import java.awt.*;

public class Bomb extends GameObject {
    private int timer = 120;
    private boolean exploded;
    private int radius = 1;
    public Bomb(int x, int y, boolean visibility) {
        super(x, y, true);
    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    protected void loadSprites(String path) {

    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isExploded() {
        return exploded;
    }
    public void update() {
        if(!exploded) {
            if (timer > 0) {
                timer--;
            }
            else {
                exploded = true;
            }
        }
    }

}
