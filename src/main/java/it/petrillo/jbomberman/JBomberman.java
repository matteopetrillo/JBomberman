package it.petrillo.jbomberman;

import it.petrillo.jbomberman.controller.GameManager;
import it.petrillo.jbomberman.view.GameFrame;
import it.petrillo.jbomberman.view.PanelTesting;

public class JBomberman {
    public static void main(String[] args) {
        GameManager gameManager= new GameManager();
        gameManager.startGame();

        /*
        GameFrame gameFrame = new GameFrame();
        PanelTesting panelTesting = new PanelTesting();
        gameFrame.getContentPane().add(panelTesting);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

         */

    }
}
