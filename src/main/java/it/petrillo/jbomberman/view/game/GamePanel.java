package it.petrillo.jbomberman.view.game;

import it.petrillo.jbomberman.controller.*;
import it.petrillo.jbomberman.model.characters.Bomberman;
import it.petrillo.jbomberman.model.gamemap.GameMap;
import it.petrillo.jbomberman.model.objects.GameObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static it.petrillo.jbomberman.util.GameConstants.*;

/**
 * The GamePanel class represents the main gameplay display area where the game is rendered and played.
 * It handles rendering game components, updating the game state, and managing interactions between
 * different game entities.
 */
public class GamePanel extends JPanel implements Runnable {

    private static final int FPS = 60;
    private static final long DRAW_INTERVAL = 1000000000 / FPS;
    private boolean running;
    private Thread gameThread = new Thread(this);
    private final Bomberman bombermanInstance = Bomberman.getPlayerInstance();
    private final ObjectsManager objectsManager = ObjectsManager.getInstance();
    private final GameMap gameMap = GameMap.getInstance();
    private final EnemyManager enemyManager = EnemyManager.getInstance();
    private final CollisionManager collisionManager = CollisionManager.getInstance();
    private final ExplosionManager explosionManager = ExplosionManager.getInstance();
    private final LevelManager levelManager = LevelManager.getInstance();
    private LoadingPanel loadingPanel;

    /**
     * Constructs a new GamePanel instance.
     * Initializes the panel's dimensions, adds a key listener for player controls, and sets up double buffering.
     */
    public GamePanel() {
        init();
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        addKeyListener(new PlayerController());
        setDoubleBuffered(true);
        setFocusable(true);
    }

    /**
     * Initializes the game components and their interactions.
     * Connects collision manager, sets up collision listener for the player, and loads the initial game level.
     */
    private void init() {
        bombermanInstance.setCollisionListener(collisionManager);
        collisionManager.addCollidable(bombermanInstance);
        levelManager.loadLevel();
    }

    /**
     * Starts the game thread, allowing the game loop to run.
     */
    public void startThread() {
        running = true;
        gameThread.start();
    }

    /**
     * Stops the game thread.
     */
    public void stopThread() {
        running = false;
        gameThread = new Thread(this);
    }

    /**
     * Paints the game components on the panel.
     *
     * @param g The Graphics object to render on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gameMap.drawMap(g2d);
        // Classic for-loop instead of an enhanced for-loop to avoid ConcurrentModification caused by double-use
        // of iterator in updating and drawing methods.
        List<GameObject> objectList = objectsManager.getObjects();
        for (int i = 0; i < objectList.size(); i++) {
            objectList.get(i).draw(g2d);
        }
        if (GameManager.GAME_STATE == GameManager.GameState.LOADING) {
            if (loadingPanel == null) {
                loadingPanel = new LoadingPanel(levelManager.getCurrentLvl());
                loadingPanel.startAnimation();
            }
            loadingPanel.draw(g2d);
        }
        else {
            try {
                explosionManager.getExplosionList().forEach(e -> e.draw(g2d));
                enemyManager.getEnemies().forEach(e -> e.draw(g2d));
                bombermanInstance.draw(g2d);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Updates game components, including the player, enemies, collisions, objects, and explosions.
     */
    private void updateComponents() {
        bombermanInstance.update();
        enemyManager.updateEnemies();
        collisionManager.checkCollisions();
        objectsManager.updateObjects();
        explosionManager.updateExplosions();
    }

    /**
     * The game loop responsible for updating the game state and rendering it.
     */
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
                if (GameManager.GAME_STATE == GameManager.GameState.PLAYING)
                    updateComponents();
                repaint();
                deltaTime--;
            }
        }
    }

    /**
     * Sets the loading panel for displaying loading animations.
     *
     * @param loadingPanel The LoadingPanel instance to set.
     */
    public void setLoadingPanel(LoadingPanel loadingPanel) {
        this.loadingPanel = loadingPanel;
    }
}
