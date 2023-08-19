package it.petrillo.jbomberman.view.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.UtilFunctions.getImg;

/**
 * The AvatarButton class represents a graphical button component that allows players to select an avatar image
 * for their character.
 * It displays different look if button is hovered over or selected.
 */
public class AvatarButton extends JButton {

    private final String avatarPath;
    private final BufferedImage normalImg;
    private final BufferedImage hoveredImg;
    private boolean hovered, checked;
    private final int x;
    private final int y;

    /**
     * Constructs a new instance of the AvatarButton class with the specified image paths and position.
     *
     * @param normalPath   The path to the normal avatar image.
     * @param hoveredPath The path to the selected avatar image.
     * @param x            The x-coordinate of the button's position.
     * @param y            The y-coordinate of the button's position.
     */
    public AvatarButton(String normalPath, String hoveredPath, int x, int y) {
        this.normalImg = getImg(normalPath);
        this.hoveredImg = getImg(hoveredPath);
        this.avatarPath = hoveredPath;
        this.x = x;
        this.y = y;
        setFocusable(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

    }

    /**
     * Draws the button's appearance based on its state (hovered, selected, or normal).
     *
     * @param g2d The Graphics2D object used for rendering.
     */
    public void draw(Graphics2D g2d) {
        if (hovered || checked)
            g2d.drawImage(hoveredImg,x,y,140,140,null);
        else
            g2d.drawImage(normalImg,x,y,140,140,null);
    }

    /**
     * Sets whether the button is being hovered over.
     *
     * @param hovered True if the button is being hovered over, false otherwise.
     */
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    /**
     * Gets the path to the selected avatar image.
     *
     * @return The path to the selected avatar image.
     */
    public String getAvatarPath() {
        return avatarPath;
    }


    /**
     * Gets the y-coordinate of the button's position.
     *
     * @return The y-coordinate of the button's position.
     */
    public int getButtonY() {
        return y;
    }

    /**
     * Gets the x-coordinate of the button's position.
     *
     * @return The x-coordinate of the button's position.
     */
    public int getButtonX() {
        return x;
    }

    /**
     * Sets whether the button is checked (selected).
     *
     * @param checked True if the button is checked (selected), false otherwise.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
