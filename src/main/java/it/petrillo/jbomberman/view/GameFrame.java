package it.petrillo.jbomberman.view;

import javax.swing.*;

import static it.petrillo.jbomberman.util.GameSettings.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("JBomberman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(getImg("/GameIcon.png"));
        setResizable(false);
    }

}
