package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.view.GameFrame;
import it.petrillo.jbomberman.view.GamePanel;

import javax.swing.*;

public class GameManager {

    private GameFrame gameFrame;
    private GamePanel gamePanel;

    public GameManager(int column, int rows) {
        this.gameFrame = new GameFrame();
        this.gamePanel = new GamePanel(column, rows);
        gameFrame.getContentPane().add(gamePanel);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
    }

    public void openFrame() {
        gameFrame.setVisible(true);
    }


}
