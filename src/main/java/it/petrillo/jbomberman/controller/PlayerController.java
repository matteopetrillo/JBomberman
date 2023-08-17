package it.petrillo.jbomberman.controller;


import it.petrillo.jbomberman.model.characters.Bomberman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static it.petrillo.jbomberman.util.GameConstants.*;

/**
 * The PlayerController class handles user input for controlling the Bomberman character.
 */
public class PlayerController implements KeyListener {

    private final Bomberman bombermanInstance = Bomberman.getPlayerInstance();
    private final ObjectsManager objectsManager = ObjectsManager.getInstance();
    private boolean moving;

    /**
     * Handles the key press event.
     *
     * @param e The key event representing the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            bombermanInstance.setMovingUp(true);
            moving = true;
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            bombermanInstance.setMovingDown(true);
            moving = true;
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            bombermanInstance.setMovingLeft(true);
            moving = true;
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            bombermanInstance.setMovingRight(true);
            moving = true;
        } else if (keyCode == KeyEvent.VK_SPACE) {
            objectsManager.dropBomb(bombermanInstance.getX()/TILE_SIZE, bombermanInstance.getY()/TILE_SIZE);
        }

        if(moving)
            AudioManager.getAudioManagerInstance().playWalkingSFX();
    }

    /**
     * Handles the key release event.
     *
     * @param e The key event representing the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            bombermanInstance.setMovingUp(false);
            moving = false;
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            bombermanInstance.setMovingDown(false);
            moving = false;
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            bombermanInstance.setMovingLeft(false);
            moving = false;
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            bombermanInstance.setMovingRight(false);
            moving = false;
        }

        if (!moving)
            AudioManager.getAudioManagerInstance().stopWalkingSFX();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
