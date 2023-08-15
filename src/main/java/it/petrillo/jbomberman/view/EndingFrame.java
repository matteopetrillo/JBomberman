package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.util.UserData;

import javax.swing.*;

import static it.petrillo.jbomberman.util.GameSettings.getImg;

public class EndingFrame extends JFrame {

    private final EndingPanel endingPanel;
    public EndingFrame(boolean playerWin, UserData userData) {
        setTitle("JBomberman");
        setIconImage(getImg("/GameIcon.png"));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.endingPanel = new EndingPanel(playerWin,userData);
        add(endingPanel);
        pack();
    }

    public EndingPanel getEndingPanel() {
        return endingPanel;
    }

    public JButton getRestartButton() {
        return endingPanel.getRestartButton();
    }
}
