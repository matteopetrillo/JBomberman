package it.petrillo.jbomberman;

import it.petrillo.jbomberman.controller.GameManager;

public class JBomberman {
    public static void main(String[] args) {
        GameManager.getInstance().openGame();
    }
}
