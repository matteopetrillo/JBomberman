package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.util.UserData;

import javax.swing.*;
import static it.petrillo.jbomberman.util.UtilFunctions.setJBombermanFrame;

/**
 * The EndingFrame class represents a JFrame that displays the ending screen of the game, either indicating
 * victory or defeat of the player.
 * It contains the EndingPanel which provides the visual elements for the ending screen.
 */
public class EndingFrame extends JFrame {

    private final EndingPanel endingPanel;

    /**
     * Constructs a new instance of the EndingFrame class with the specified victory status and user data.
     *
     * @param playerWin True if the player has won, false if the player has lost.
     * @param userData  The user data containing player information.
     */
    public EndingFrame(boolean playerWin, UserData userData) {
        setJBombermanFrame(this);
        this.endingPanel = new EndingPanel(playerWin,userData);
        add(endingPanel);
        pack();
    }

    /**
     * Gets the restart button from the EndingPanel.
     *
     * @return The restart button.
     */
    public JButton getRestartButton() {
        return endingPanel.getRestartButton();
    }
}
