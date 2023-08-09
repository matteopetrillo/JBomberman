package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.view.GameFrame;
import it.petrillo.jbomberman.view.GamePanel;
import it.petrillo.jbomberman.view.PlayerPanel;

import java.awt.*;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class GameManager  {

    private final GameFrame gameFrame = new GameFrame();
    private final GamePanel gamePanel = new GamePanel();
    private final PlayerPanel playerPanel = new PlayerPanel();
    private Bomberman bomberman = Bomberman.getPlayerInstance();
    public GameManager() {
        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(gamePanel,BorderLayout.CENTER);
        gameFrame.add(playerPanel,BorderLayout.NORTH);
        gameFrame.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT+188));
        gameFrame.setLocationRelativeTo(null);
        bomberman.addObserver(playerPanel);
        bomberman.addObserver(gamePanel);
    }

    public void startGame() {
        gamePanel.startThread();
        gameFrame.setVisible(true);
        System.out.println("******** Game Started! ********");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        GAME_STATE = GameState.PLAYING;
        playerPanel.startTimer();
    }

}
