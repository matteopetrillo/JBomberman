package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.model.Player;
import it.petrillo.jbomberman.util.*;
import it.petrillo.jbomberman.util.GameUtils.ObjectVisibility;
import it.petrillo.jbomberman.util.LevelSettings;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements CustomObserver {

    private int column,rows;
    private int screenWidth, screenHeight;
    private Player playerRef;

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
        if (playerRef.getObjectVisibility() == ObjectVisibility.VISIBLE)
            drawPlayer(g2d);

    }

    public void drawPlayer(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(playerRef.getX(), playerRef.getY(), GameUtils.Tile.SIZE.getValue(), GameUtils.Tile.SIZE.getValue());
    }
    @Override
    public void update(SenderType senderType, Object arg) {
        if (arg instanceof Player) {
            this.playerRef = (Player) arg;
        }
    }
}
