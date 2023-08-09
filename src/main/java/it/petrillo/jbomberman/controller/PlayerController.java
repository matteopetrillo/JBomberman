package it.petrillo.jbomberman.controller;


import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.util.GameUtils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class PlayerController implements KeyListener {
    private final Bomberman bombermanInstance = Bomberman.getPlayerInstance();
    private final ObjectsManager objectsManager = ObjectsManager.getInstance();

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            bombermanInstance.setMovingUp(true);
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            bombermanInstance.setMovingDown(true);
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            bombermanInstance.setMovingLeft(true);
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            bombermanInstance.setMovingRight(true);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            objectsManager.dropBomb(bombermanInstance.getX()/TILE_SIZE, bombermanInstance.getY()/TILE_SIZE);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            bombermanInstance.setMovingUp(false);
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            bombermanInstance.setMovingDown(false);
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            bombermanInstance.setMovingLeft(false);
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            bombermanInstance.setMovingRight(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
