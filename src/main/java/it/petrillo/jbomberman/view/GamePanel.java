package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.controller.BombManager;
import it.petrillo.jbomberman.controller.EnemyManager;
import it.petrillo.jbomberman.model.*;
import it.petrillo.jbomberman.util.*;
import it.petrillo.jbomberman.util.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GamePanel extends JPanel implements CustomObserver {

    private Player player = Player.getPlayerInstance();
    private EnemyManager enemyManager;
    private GameMap gameMap = GameMap.getInstance();
    private SpriteRenderer spriteRenderer;
    private BombManager bombManager;

    public void initialize(Settings settings) {
        int screenHeight = settings.getScreenHeight();
        int screenWidth = settings.getScreenWidth();
        setPreferredSize(new Dimension(screenWidth, screenHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawMap(g2d);

        enemyManager.getEnemies()
                    .stream()
                    .filter(e -> e.isVisible())
                    .forEach(e -> spriteRenderer.drawEnemies(g2d,e.getX(),e.getY()));

        bombManager.getBombs()
                    .stream()
                    .forEach(bomb -> {
                        if (!bomb.isExploded())
                            spriteRenderer.drawBomb(g2d, bomb.getX(), bomb.getY());
                    });

        drawPlayer(g2d);

    }
    
    private void drawMap(Graphics2D g) {
        if (gameMap != null && spriteRenderer.getTilesImg() != null) {
            Map<Integer, BufferedImage> tileImg = spriteRenderer.getTilesImg();
            MapTile[][] mapTiles = gameMap.getMapTiles();
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

    private void drawPlayer(Graphics2D g2d) {
        if (player.isVisible()) {
            int x = player.getX();
            int y = player.getY();
            spriteRenderer.drawPlayer(g2d, x, y);
            g2d.setColor(Color.RED);
            g2d.draw(player.getCollisionBox());
        }
    }

    @Override
    public void update(NotificationType notificationType, Object arg) {
        if (notificationType == NotificationType.DROP_BOMB) {
            bombManager.newBomb(player.getCollisionBox().x, player.getCollisionBox().y);
        }
    }

    public void setBombManager(BombManager bombManager) {
        this.bombManager = bombManager;
    }

    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public void setSpriteRenderer(SpriteRenderer spriteRenderer) {
        this.spriteRenderer = spriteRenderer;
    }

}
