package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.GameMap;
import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.view.GameFrame;
import it.petrillo.jbomberman.view.GamePanel;

public class GameManager  {

    private final GameFrame gameFrame = new GameFrame();
    private final GamePanel gamePanel = new GamePanel();

    public GameManager() {
        gameFrame.getContentPane().add(gamePanel);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
    }

    public void startGame() {
        gamePanel.startThread();
        gameFrame.setVisible(true);
        System.out.println("******** Game Started! ********");
    }

}
