package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.util.GameUtils;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("JBomberman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(GameUtils.getImg("/GameIcon.png"));
        setResizable(false);
    }

}
