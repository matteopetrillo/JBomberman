package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.Player;
import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.JsonLoader;
import it.petrillo.jbomberman.util.LevelSettings;
import it.petrillo.jbomberman.view.GameFrame;
import it.petrillo.jbomberman.view.GamePanel;
import it.petrillo.jbomberman.view.SpriteRenderer;

public class GameManager {

    private GameFrame gameFrame;
    private GamePanel gamePanel;
    private Player player;
    private SpriteRenderer spriteRenderer;
    private CollisionManager collisionManager;

    public GameManager() {
        this.gameFrame = new GameFrame();
        this.gamePanel = new GamePanel();
        gameFrame.getContentPane().add(gamePanel);
        this.player = Player.getPlayerInstance();
        this.player.addObserver(gamePanel);
        this.spriteRenderer = new SpriteRenderer();
        gamePanel.setSpriteRenderer(spriteRenderer);
        update();
    }

    public void startGame() {
        LevelSettings levelSettings = getSettings(GameUtils.LevelSettings.LEVEL_1.getValue());
        gamePanel.initialize(levelSettings);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    private void update() {
        spriteRenderer.setPlayerPosition(player.getPosition());
    }
    private LevelSettings getSettings(String path) {
        return JsonLoader.loadJson(path, LevelSettings.class);
    }

}
