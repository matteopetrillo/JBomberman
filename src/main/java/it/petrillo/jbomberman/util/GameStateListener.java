package it.petrillo.jbomberman.util;

public interface GameStateListener {
    void onLosing();
    void onWinning();
    void onLoading();
    void onPlaying();
}
