package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.util.CustomObserver;
import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.NotificationType;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements CustomObserver {

    private SpriteRenderer spriteRenderer = new SpriteRenderer();
    private int column,rows;
    private int screenWidth, screenHeight;

    public GamePanel (int column, int rows) {
        this.column = column;
        this.rows = rows;
        this.screenHeight = rows * GameUtils.TILE_SIZE.getValue();
        this.screenWidth = column * GameUtils.TILE_SIZE.getValue();
        setPreferredSize(new Dimension(screenHeight, screenWidth));
        setBackground(Color.BLACK);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
    }

    @Override
    public void update(NotificationType notificationType, Object arg) {

    }
}
