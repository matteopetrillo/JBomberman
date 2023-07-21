package it.petrillo.jbomberman.controller;


import it.petrillo.jbomberman.model.Bomberman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerKeyHandler implements KeyListener {
    private Bomberman bombermanInstance = Bomberman.getPlayerInstance();

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            bombermanInstance.setMovingUp(true);
        } else if (keyCode == KeyEvent.VK_S) {
            bombermanInstance.setMovingDown(true);
        } else if (keyCode == KeyEvent.VK_A) {
            bombermanInstance.setMovingLeft(true);
        } else if (keyCode == KeyEvent.VK_D) {
            bombermanInstance.setMovingRight(true);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            bombermanInstance.dropBomb();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            bombermanInstance.setMovingUp(false);
        } else if (keyCode == KeyEvent.VK_S) {
            bombermanInstance.setMovingDown(false);
        } else if (keyCode == KeyEvent.VK_A) {
            bombermanInstance.setMovingLeft(false);
        } else if (keyCode == KeyEvent.VK_D) {
            bombermanInstance.setMovingRight(false);
        }
    }
}
