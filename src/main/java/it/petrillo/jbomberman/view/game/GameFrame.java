package it.petrillo.jbomberman.view.game;

import javax.swing.*;

import static it.petrillo.jbomberman.util.UtilFunctions.getImg;
import static it.petrillo.jbomberman.util.UtilFunctions.setJBombermanFrame;

/**
 * The GameFrame class represents the main game window. It extends the JFrame class
 * and provides a custom configuration for the game's frame appearance and behavior.
 */
public class GameFrame extends JFrame {

    /**
     * Creates a new instance of the game frame and apply customization to it.
     */
    public GameFrame() {
        setJBombermanFrame(this);
    }

}
