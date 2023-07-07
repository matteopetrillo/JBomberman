package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.controller.EnemyManager;
import it.petrillo.jbomberman.model.Enemy;
import it.petrillo.jbomberman.model.MapTile;
import it.petrillo.jbomberman.model.Player;
import it.petrillo.jbomberman.util.*;
import it.petrillo.jbomberman.util.GameUtils.ObjectVisibility;
import it.petrillo.jbomberman.util.Settings;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements CustomObserver {

    private int screenWidth, screenHeight;
    private Player playerRef;
    private EnemyManager enemyManager = new EnemyManager();
    private MapTile[][] mapTiles;
    private SpriteRenderer spriteRenderer = new SpriteRenderer();

    public void initialize(Settings settings) {
        this.screenHeight = settings.getScreenHeight();
        this.screenWidth = settings.getScreenWidth();
        enemyManager.initEnemies(settings);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (mapTiles != null) {
            for (int i = 0; i < mapTiles.length; i++) {
                for (int j = 0; j < mapTiles[i].length; j++) {
                    MapTile mapTile = mapTiles[i][j];
                    int x = j * Settings.TILE_SIZE;
                    int y = i * Settings.TILE_SIZE;

                    if (mapTile.isWalkable()) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.GRAY);
                    }
                    g.fillRect(x, y, Settings.TILE_SIZE, Settings.TILE_SIZE);
                }
            }
        }

        enemyManager.getEnemies().stream().filter(e -> e.getObjectVisibility().equals(ObjectVisibility.VISIBLE))
                .forEach(e -> spriteRenderer.drawEnemies(g2d,e.getX(),e.getY()));
        
        if (playerRef.getObjectVisibility() == ObjectVisibility.VISIBLE) {
            int playerX = playerRef.getX();
            int playerY = playerRef.getY();
            spriteRenderer.drawPlayer(g2d, playerX, playerY);
        }


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
