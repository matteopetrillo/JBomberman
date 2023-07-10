package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.GameMap;
import it.petrillo.jbomberman.model.Player;
import it.petrillo.jbomberman.util.JsonLoader;
import it.petrillo.jbomberman.util.Settings;
import it.petrillo.jbomberman.view.GameFrame;
import it.petrillo.jbomberman.view.GamePanel;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GameManager implements Runnable {

    private static final int FPS = 60;
    private static final long DRAW_INTERVAL = 1000000000 / FPS;
    private GameFrame gameFrame = new GameFrame();
    private GamePanel gamePanel = new GamePanel();
    private Player playerInstance;
    private CollisionManager collisionManager;
    boolean running;

    public GameManager() {
        gamePanel.addKeyListener(new PlayerController());
        playerInstance = Player.getPlayerInstance();
        playerInstance.addObserver(gamePanel);
        gameFrame.getContentPane().add(gamePanel);
        gamePanel.setDoubleBuffered(true);
        gamePanel.setFocusable(true);
    }

    public void startGame() {
        Settings settings = importSettings(SettingsPath.LEVEL_1.getValue());
        GameMap gameMap = new GameMap(settings.getMapFilePath());
        collisionManager = new CollisionManager(gameMap);
        collisionManager.addCollidable(playerInstance);
        playerInstance.setCollisionListener(collisionManager);
        gamePanel.setMapTiles(gameMap.getMapTiles());
        new Thread(this).start();
        running = true;
        gamePanel.initialize(settings);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    public void stopGame() {running = false;}
    private void update() {
        playerInstance.updateStatus();
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
