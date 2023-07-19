package it.petrillo.jbomberman.model;

public class Bomb extends GameEntity{
    private int timer = 120;
    private boolean exploded;
    private int radius = 1;
    public Bomb(int x, int y, boolean visibility) {
        super(x, y, true);
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
