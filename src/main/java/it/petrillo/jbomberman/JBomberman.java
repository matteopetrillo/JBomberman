package it.petrillo.jbomberman;

import it.petrillo.jbomberman.controller.GameManager;

/**
 * The main class of the JBomberman game application.
 * This class contains the main method that initializes and starts the game
 * by invoking the openGame() method of the GameManager.
 */
public class JBomberman {
    public static void main(String[] args) {
        GameManager.getInstance().openGame();
    }
}
