package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.controller.BombManager;
import it.petrillo.jbomberman.controller.EnemyManager;
import it.petrillo.jbomberman.controller.PlayerKeyHandler;
import it.petrillo.jbomberman.model.*;
import it.petrillo.jbomberman.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GamePanel extends JPanel implements CustomObserver {

    private Bomberman bomberman = Bomberman.getPlayerInstance();
    private EnemyManager enemyManager;
    private GameMap gameMap = GameMap.getInstance();
    private BombManager bombManager;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        addKeyListener(new PlayerKeyHandler());
        setDoubleBuffered(true);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gameMap.drawMap(g2d);
        bomberman.draw(g2d);

    }


    @Override
    public void update(NotificationType notificationType, Object arg) {
        if (notificationType == NotificationType.DROP_BOMB) {
            bombManager.newBomb(bomberman.getCollisionBox().x, bomberman.getCollisionBox().y);
        }
    }

    public void setBombManager(BombManager bombManager) {
        this.bombManager = bombManager;
    }

    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }


}
