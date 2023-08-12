package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.controller.*;
import it.petrillo.jbomberman.model.*;
import it.petrillo.jbomberman.util.CustomObserver;

import javax.swing.*;
import java.awt.*;

import static it.petrillo.jbomberman.util.GameSettings.*;

/**
 * This class represents the game panel where the gameplay is displayed.
 */
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
        collisionManager.setGameMap(gameMap);
        collisionManager.setObjectsManager(objectsManager);
        bombermanInstance.setCollisionListener(collisionManager);
        collisionManager.addCollidable(bombermanInstance);
        levelManager.loadLevel();
    }

    /**
     * Starts the game thread, allowing the game loop to run.
     */
    public void startThread() {
        running = true;
        Thread gameThread = new Thread(this);
        gameThread.start();
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
        if (GameManager.GAME_STATE == GameState.LOADING) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial",Font.PLAIN,60));
            g2d.drawString("LOADING",400,400);
        }
        else {
            gameMap.drawMap(g2d);
            try {
                objectsManager.getObjects().forEach(o -> o.draw(g2d));
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
                if (GameManager.GAME_STATE == GameState.PLAYING) {
                    updateComponents();
                    repaint();
                }
                deltaTime--;
            }
        }
    }

}
