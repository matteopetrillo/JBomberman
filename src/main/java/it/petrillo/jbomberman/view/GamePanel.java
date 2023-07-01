package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.util.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements CustomObserver {

    private SpriteRenderer spriteRenderer;
    private int column,rows;
    private int screenWidth, screenHeight;

    public void initialize(LevelSettings levelSettings) {
        this.column = levelSettings.getMapColumns();
        this.rows = levelSettings.getMapRows();
        this.screenHeight = rows * GameUtils.Tile.SIZE.getValue();
        this.screenWidth = column * GameUtils.Tile.SIZE.getValue();
        setPreferredSize(new Dimension(screenHeight, screenWidth));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        spriteRenderer.drawPlayer(g2d);
    }

    public void setSpriteRenderer(SpriteRenderer spriteRenderer) {
        this.spriteRenderer = spriteRenderer;
    }

    @Override
    public void update(NotificationType notificationType, Object arg) {
    }
}
