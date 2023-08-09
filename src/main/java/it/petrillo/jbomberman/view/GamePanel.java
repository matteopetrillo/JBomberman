package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.controller.*;
import it.petrillo.jbomberman.model.*;

import javax.swing.*;
import java.awt.*;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GamePanel extends JPanel implements Runnable {

    private static final int FPS = 60;
    private static final long DRAW_INTERVAL = 1000000000 / FPS;
    boolean running;
    private final Bomberman bombermanInstance = Bomberman.getPlayerInstance();
    private final ObjectsManager objectsManager = ObjectsManager.getInstance();
    private final GameMap gameMap = GameMap.getInstance();
    private final EnemyManager enemyManager = EnemyManager.getInstance();
    private final CollisionManager collisionManager = CollisionManager.getInstance();
    private final ExplosionManager explosionManager = ExplosionManager.getInstance();
    private final LevelManager levelManager = LevelManager.getInstance();

    public GamePanel() {
        init();
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        addKeyListener(new PlayerController());
        setDoubleBuffered(true);
        setFocusable(true);
    }

    private void init() {
        collisionManager.setGameMap(gameMap);
        collisionManager.setObjectsManager(objectsManager);
        bombermanInstance.setCollisionListener(collisionManager);
        collisionManager.addCollidable(bombermanInstance);
        levelManager.loadLevel();
    }

    public void startThread() {
        running = true;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gameMap.drawMap(g2d);
        try {
            objectsManager.getObjects().forEach(o -> o.draw(g2d));
            explosionManager.getExplosionList().forEach(e -> e.draw(g2d));
            enemyManager.getEnemies().forEach(e -> e.draw(g2d));
            bombermanInstance.draw(g2d);
        } catch (NullPointerException e)
        {
            e.getMessage();
        }
    }

    private void updateComponents() {
        bombermanInstance.update();
        enemyManager.updateEnemies();
        collisionManager.checkCollisions();
        objectsManager.updateObjects();
        explosionManager.updateExplosions();
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long currentTime;
        double deltaTime = 0;

        while (running) {
            currentTime = System.nanoTime();
            deltaTime += (double) (currentTime - lastTime) / DRAW_INTERVAL;
            lastTime = currentTime;
            if (deltaTime >= 1) {
                updateComponents();
                repaint();
                deltaTime--;
            }
        }
    }
}
