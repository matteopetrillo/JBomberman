package it.petrillo.jbomberman.model;

public class Player extends GameEntity {

    private static Player playerInstance;
    private Player(int x, int y) {
        super(x, y);
        super.setVisible(true);
    }

    public static Player getPlayerInstance() {
        if(playerInstance == null) {
            playerInstance = new Player(0,0);
        }
        return playerInstance;
    }
}
