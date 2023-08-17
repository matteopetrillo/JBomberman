package it.petrillo.jbomberman.view.game;

import javax.swing.*;

import static it.petrillo.jbomberman.util.GameConstants.*;
import static it.petrillo.jbomberman.util.UtilFunctions.getImg;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("JBomberman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(getImg("/GameIcon.png"));
        setResizable(false);
    }

}
