package it.petrillo.jbomberman;

import it.petrillo.jbomberman.controller.GameManager;

public class Main {
    public static void main(String[] args) {
        GameManager gameManager= new GameManager();
        gameManager.startGame();
    }
}
