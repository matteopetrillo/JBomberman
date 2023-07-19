package it.petrillo.jbomberman.controller;


import it.petrillo.jbomberman.model.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener {
    private Player playerInstance = Player.getPlayerInstance();

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            playerInstance.setMovingUp(true);
        } else if (keyCode == KeyEvent.VK_S) {
            playerInstance.setMovingDown(true);
        } else if (keyCode == KeyEvent.VK_A) {
            playerInstance.setMovingLeft(true);
        } else if (keyCode == KeyEvent.VK_D) {
            playerInstance.setMovingRight(true);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            playerInstance.dropBomb();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            playerInstance.setMovingUp(false);
        } else if (keyCode == KeyEvent.VK_S) {
            playerInstance.setMovingDown(false);
        } else if (keyCode == KeyEvent.VK_A) {
            playerInstance.setMovingLeft(false);
        } else if (keyCode == KeyEvent.VK_D) {
            playerInstance.setMovingRight(false);
        }
    }
}
