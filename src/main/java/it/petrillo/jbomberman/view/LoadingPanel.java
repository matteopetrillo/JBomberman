package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.util.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static it.petrillo.jbomberman.util.GameSettings.*;

public class LoadingPanel {
    private String message;
    private int state;
    private Timer timer;
    private boolean started = false;
    int currentLvl;
    int alpha = 255;
    long startTime;
    public LoadingPanel(int lvl) {
        this.currentLvl = lvl;
        this.message = "Level "+currentLvl+"!";
    }
    public void startAnimation() {
        if (!started) {
            timer = new Timer(10, new ActionListener() {
                private long startTime = System.currentTimeMillis();

                @Override
                public void actionPerformed(ActionEvent e) {
                    long elapsedTime = System.currentTimeMillis() - startTime;

                    if (state == 0) {
                        alpha -= 2;
                        if (alpha <= 60) {
                            alpha = 60;
                            state = 1;
                        }
                    } else if (state == 1 && elapsedTime >= 500) {
                        message = "Start!";
                        state = 2;
                    } else if (state == 2) {
                        timer.stop();
                    }
                }
            });
            timer.start();
            started = true;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, alpha));
        g2d.fillRect(0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

        g2d.setFont(RETRO_FONT.deriveFont(Font.PLAIN,70f));

        Font font = RETRO_FONT.deriveFont(Font.PLAIN, 70f);
        g2d.setFont(font);

        FontMetrics metrics = g2d.getFontMetrics(font);
        int textWidth = metrics.stringWidth(message);

        int x = (GameSettings.SCREEN_WIDTH - textWidth) / 2;
        int y = (GameSettings.SCREEN_HEIGHT) / 2;

        g2d.setColor(Color.BLACK);
        g2d.drawString(message, x - 4, y - 4);
        g2d.drawString(message, x + 4, y - 4);
        g2d.drawString(message, x - 4, y + 4);
        g2d.drawString(message, x + 4, y + 4);

        g2d.setColor(Color.ORANGE);
        g2d.drawString(message, x, y);
    }
}
