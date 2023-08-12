package it.petrillo.jbomberman.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameSettings.*;


public class AvatarButton extends JButton {

    private String avatarPath;
    private BufferedImage normalImg;
    private BufferedImage selectedImg;
    private boolean isHover, isSelected;
    private int x,y;
    public AvatarButton(String normalPath, String selectedPath, int x, int y) {
        this.normalImg = getImg(normalPath);
        this.selectedImg = getImg(selectedPath);
        this.avatarPath = selectedPath;
        this.x = x;
        this.y = y;
        setFocusable(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

    }

    public void draw(Graphics2D g2d) {
        if (isHover || isSelected)
            g2d.drawImage(selectedImg,x,y,140,140,null);
        else
            g2d.drawImage(normalImg,x,y,140,140,null);
    }
    public void setHover(boolean hover) {
        this.isHover = hover;
    }

    public boolean isHover() {
        return isHover;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }
}
