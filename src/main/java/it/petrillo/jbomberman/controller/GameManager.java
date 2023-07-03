package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.Player;
import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.JsonLoader;
import it.petrillo.jbomberman.util.LevelSettings;
import it.petrillo.jbomberman.view.GameFrame;
import it.petrillo.jbomberman.view.GamePanel;

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
        LevelSettings levelSettings = getSettings(GameUtils.LevelSettings.LEVEL_1.getValue());
        new Thread(this).start();
        running = true;
        gamePanel.initialize(levelSettings);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    public void stopGame() {running = false;}
    private void update() {
        playerInstance.updateStatus();
    }

    private LevelSettings getSettings(String path) {
        return JsonLoader.loadJson(path, LevelSettings.class);
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
