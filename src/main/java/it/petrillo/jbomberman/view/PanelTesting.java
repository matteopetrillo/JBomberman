package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.controller.PlayerController;
import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.util.CustomObserver;

import javax.swing.*;
import java.awt.*;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class PanelTesting extends JPanel implements Runnable, CustomObserver {

    private Bomberman bomberman = Bomberman.getPlayerInstance();
    private static final int FPS = 60;
    private static final long DRAW_INTERVAL = 1000000000 / FPS;
    private boolean running;
    public PanelTesting() {
        setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        addKeyListener(new PlayerController());
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        new Thread(this).start();
        running = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        bomberman.draw(g2d);
    }



    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long currentTime;
        double deltaTime = 0;

        while (running) {
            currentTime = System.nanoTime();
            deltaTime += (double) (currentTime - lastTime) / DRAW_INTERVAL;
            lastTime = currentTime;
            if (deltaTime >= 1) {
                update();
                repaint();
                deltaTime--;
            }
        }
    }

    private void update() {
        bomberman.update();
    }

    @Override
    public void update(NotificationType notificationType, Object arg) {

    }
}
