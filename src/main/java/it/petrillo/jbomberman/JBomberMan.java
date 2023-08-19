package it.petrillo.jbomberman;

import it.petrillo.jbomberman.controller.GameManager;

/**
 * The main class of the JBomberMan game application.
 * This class contains the main method that initializes and open the game's menu
 * by invoking the openMenu() method of the GameManager.
 */
public class JBomberMan {
    public static void main(String[] args) {
        GameManager.getInstance().openMenu();
    }
}
