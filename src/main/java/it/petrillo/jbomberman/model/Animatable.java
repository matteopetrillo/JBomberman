package it.petrillo.jbomberman.model;

import java.awt.*;

public interface Animatable {
    void draw(Graphics2D g);
    void loadSprites(String normalPath, String hittedPath);
    void update();


}
