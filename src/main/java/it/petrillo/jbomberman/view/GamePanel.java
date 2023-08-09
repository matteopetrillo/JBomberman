package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.controller.*;
import it.petrillo.jbomberman.model.*;
import it.petrillo.jbomberman.util.CustomObserver;

import javax.swing.*;
import java.awt.*;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GamePanel extends JPanel implements CustomObserver,Runnable {

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
        if (GAME_STATE == GameState.PLAYING) {
            gameMap.drawMap(g2d);
            try {
                objectsManager.getObjects().forEach(o -> o.draw(g2d));
                explosionManager.getExplosionList().forEach(e -> e.draw(g2d));
                enemyManager.getEnemies().forEach(e -> e.draw(g2d));
                bombermanInstance.draw(g2d);
            } catch (Exception e) {
                e.getMessage();
            }
        } else if (GAME_STATE == GameState.LOADING) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial",Font.PLAIN,60));
            g2d.drawString("LOADING",400,400);
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

    @Override
    public void update(NotificationType notificationType, Object arg) {
        switch (notificationType) {
            case FINISH_LEVEL -> {
                if (enemyManager.getEnemies().isEmpty()) {
                    GAME_STATE = GameState.LOADING;
                    Timer timer = new Timer(2000, e -> {
                        levelManager.loadNextLevel();
                        GAME_STATE = GameState.PLAYING;
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            }
            case GAME_OVER -> {
            }
        }
    }
}
