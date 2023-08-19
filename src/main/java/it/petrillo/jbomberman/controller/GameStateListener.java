package it.petrillo.jbomberman.controller;

/**
 * The GameStateListener interface defines the contract for objects that listen to changes in the game's state.
 * Implementing classes will receive notifications when the game state transitions between different phases.
 */
public interface GameStateListener {

    /**
     * Invoked when the game is in a state of losing, indicating the player's failure.
     */
    void onLosing();

    /**
     * Invoked when the game is in a state of winning, indicating the player's success.
     */
    void onWinning();

    /**
     * Invoked when the game is in the process of loading, preparing resources or levels.
     */
    void onLoading();

    /**
     * Invoked when the game is in a state of active play.
     */
    void onPlaying();
}
