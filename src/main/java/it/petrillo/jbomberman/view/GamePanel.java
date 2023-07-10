package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.controller.EnemyManager;
import it.petrillo.jbomberman.model.Enemy;
import it.petrillo.jbomberman.model.MapTile;
import it.petrillo.jbomberman.model.Player;
import it.petrillo.jbomberman.util.*;
import it.petrillo.jbomberman.util.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

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

        drawMap(g2d);
        /*
        for (int i = 0; i < mapTiles.length; i++) {
            for (int j = 0; j < mapTiles[i].length; j++) {
                MapTile tile = mapTiles[i][j];
                g2d.setColor(Color.RED);
                g2d.draw(tile.getCollisionBox());
            }
        }

         */

        enemyManager.getEnemies().stream().filter(e -> e.isVisible())
                .forEach(e -> spriteRenderer.drawEnemies(g2d,e.getX(),e.getY()));
        
        if (playerRef.isVisible()) {
            int playerX = playerRef.getX();
            int playerY = playerRef.getY();
            spriteRenderer.drawPlayer(g2d, playerX, playerY, playerRef.getCollisionBox());
        }


    }
    
    private void drawMap(Graphics2D g) {
        if (mapTiles != null && spriteRenderer.getTilesImg() != null) {
            Map<Integer, BufferedImage> tileImg = spriteRenderer.getTilesImg();
            for (int i = 0; i < mapTiles.length; i++) {
                for (int j = 0; j < mapTiles[i].length; j++) {
                    MapTile tile = mapTiles[i][j];
                    BufferedImage img = tileImg.get(tile.getTileID());

                    int x = j*Settings.TILE_SIZE;
                    int y = i*Settings.TILE_SIZE;

                    g.drawImage(img,x,y,Settings.TILE_SIZE, Settings.TILE_SIZE,null);
                }
            }
            
            
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
