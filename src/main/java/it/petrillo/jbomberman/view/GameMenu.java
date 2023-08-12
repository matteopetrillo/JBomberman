package it.petrillo.jbomberman.view;

import javax.swing.*;

import static it.petrillo.jbomberman.util.GameSettings.getImg;


public class GameMenu extends JFrame {
    public GameMenu() {
        setTitle("JBomberman");
        setIconImage(getImg("/GameIcon.png"));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
