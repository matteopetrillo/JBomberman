package it.petrillo.jbomberman.view;

import javax.swing.*;

import static it.petrillo.jbomberman.util.GameSettings.getImg;


public class MenuFrame extends JFrame {
    public MenuFrame() {
        setTitle("JBomberman");
        setIconImage(getImg("/GameIcon.png"));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
