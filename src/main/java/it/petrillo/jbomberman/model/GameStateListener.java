package it.petrillo.jbomberman.model;

public interface GameStateListener {
    void onLosing();
    void onWinning();
    void onLoading();
    void onPlaying();
}
