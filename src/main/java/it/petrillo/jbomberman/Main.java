package it.petrillo.jbomberman;

import it.petrillo.jbomberman.controller.GameInitializer;
import it.petrillo.jbomberman.model.LevelSettings;
import it.petrillo.jbomberman.util.JsonLoader;

public class Main {
    public static void main(String[] args) {
        GameInitializer gameInitializer = new GameInitializer();
        gameInitializer.initGame();
        gameInitializer.startGame();
    }
}
