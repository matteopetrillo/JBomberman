package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.model.MapTile;
import it.petrillo.jbomberman.model.Player;
import it.petrillo.jbomberman.util.*;
import it.petrillo.jbomberman.util.GameUtils.ObjectVisibility;
import it.petrillo.jbomberman.util.Settings;

import javax.swing.*;
import java.awt.*;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GamePanel extends JPanel implements CustomObserver {

    private int screenWidth, screenHeight;
    private Player playerRef;
    private MapTile[][] mapTiles;

    public void initialize(Settings settings) {
        this.screenHeight = settings.getScreenHeight();
        this.screenWidth = settings.getScreenWidth();
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (mapTiles != null) {
            for (int row = 0; row < mapTiles.length; row++) {
                for (int col = 0; col < mapTiles[row].length; col++) {
                    MapTile mapTile = mapTiles[row][col];
                    int x = col * Tile.SIZE.getValue();
                    int y = row * Tile.SIZE.getValue();

                    // Disegna la mapTile sul pannello
                    if (mapTile.isWalkable()) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(x, y, Tile.SIZE.getValue(), Tile.SIZE.getValue());
                }
            }
        }
        if (playerRef.getObjectVisibility() == ObjectVisibility.VISIBLE)
            drawPlayer(g2d);

    }

    private void drawPlayer(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(playerRef.getX(), playerRef.getY(), Tile.SIZE.getValue(), Tile.SIZE.getValue());
        Rectangle hitbox = playerRef.getCollisionBox();
        g.setColor(Color.BLUE);
        g.draw(hitbox);
    }
    @Override
    public void update(SenderType senderType, Object arg) {
        if (arg instanceof Player) {
            this.playerRef = (Player) arg;
        }
    }

    public void setMapTiles(MapTile[][] mapTiles) {
        this.mapTiles = mapTiles;
    }
}
