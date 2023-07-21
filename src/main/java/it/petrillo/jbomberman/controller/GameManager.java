package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.GameMap;
import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.util.JsonLoader;
import it.petrillo.jbomberman.util.Settings;
import it.petrillo.jbomberman.view.GameFrame;
import it.petrillo.jbomberman.view.GamePanel;
import it.petrillo.jbomberman.view.SpriteRenderer;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GameManager implements Runnable {

    private static final int FPS = 60;
    private static final long DRAW_INTERVAL = 1000000000 / FPS;
    private Settings gameSettings;
    private GameFrame gameFrame = new GameFrame();
    private GamePanel gamePanel = new GamePanel();
    private Bomberman bombermanInstance = Bomberman.getPlayerInstance();
    private CollisionManager collisionManager = CollisionManager.getInstance();
    private EnemyManager enemyManager = new EnemyManager();
    private BombManager bombManager = new BombManager();
    private GameMap gameMap = GameMap.getInstance();
    boolean running;

    public GameManager() {
        gameSettings = importSettings(SettingsPath.LEVEL_1.getValue());
        gamePanel.addKeyListener(new PlayerKeyHandler());
        bombermanInstance.addObserver(gamePanel);
        gameFrame.getContentPane().add(gamePanel);
        gamePanel.setDoubleBuffered(true);
        gamePanel.setFocusable(true);
    }

    public void startGame() {
        gameMap.initMap(gameSettings.getMapFilePath());
        collisionManager.setGameMap(gameMap);
        collisionManager.addCollidable(bombermanInstance);
        bombermanInstance.setCollisionListener(collisionManager);
        gamePanel.setEnemyManager(enemyManager);
        gamePanel.setBombManager(bombManager);
        gamePanel.setSpriteRenderer(new SpriteRenderer());
        new Thread(this).start();
        running = true;
        gamePanel.initialize(gameSettings);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    public void stopGame() {running = false;}
    private void update() {
        bombermanInstance.updateStatus();
        bombManager.updateBombs();
    }

    private Settings importSettings(String path) {
        return JsonLoader.loadJson(path, Settings.class);
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
                update();
                gamePanel.repaint();
                deltaTime--;
            }
        }
    }
}
