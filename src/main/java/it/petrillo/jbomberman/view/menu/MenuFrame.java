package it.petrillo.jbomberman.view.menu;

import javax.swing.*;

import static it.petrillo.jbomberman.util.UtilFunctions.getImg;


public class MenuFrame extends JFrame {
    public MenuFrame() {
        setTitle("JBomberman");
        setIconImage(getImg("/GameIcon.png"));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
