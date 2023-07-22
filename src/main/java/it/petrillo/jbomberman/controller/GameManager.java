package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.GameMap;
import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.view.GameFrame;
import it.petrillo.jbomberman.view.GamePanel;

import java.util.logging.Level;

public class GameManager implements Runnable {

    private static final int FPS = 60;
    private static final long DRAW_INTERVAL = 1000000000 / FPS;
    private GameFrame gameFrame = new GameFrame();
    private GamePanel gamePanel = new GamePanel();
    private Bomberman bombermanInstance = Bomberman.getPlayerInstance();
    private CollisionManager collisionManager = CollisionManager.getInstance();
    private GameMap gameMap = GameMap.getInstance();
    private ObjectsManager objectsManager = ObjectsManager.getInstance();
    private LevelManager levelManager = LevelManager.getInstance();
    boolean running;

    public GameManager() {
        bombermanInstance.addObserver(gamePanel);
        gameFrame.getContentPane().add(gamePanel);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        collisionManager.setGameMap(gameMap);
        bombermanInstance.setCollisionListener(collisionManager);
        levelManager.loadLevel();
    }

    public void startGame() {
        new Thread(this).start();
        running = true;
        gameFrame.setVisible(true);
        System.out.println("******** Game Started! ********");
    }

    public void stopGame() {running = false;}
    private void update() {
        bombermanInstance.updateStatus();
        objectsManager.updateObjects();
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
