package it.petrillo.jbomberman.view.game;

import it.petrillo.jbomberman.util.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static it.petrillo.jbomberman.util.GameConstants.*;
import static it.petrillo.jbomberman.util.UtilFunctions.drawBorder;

/**
 * The LoadingPanel class provides a panel for displaying loading animations and messages before starting a game level.
 */
public class LoadingPanel {

    private String message;
    private int state;
    private Timer timer;
    private boolean started = false;
    private int alpha = 255;

    /**
     * Creates a LoadingPanel instance for the specified level.
     *
     * @param lvl The level number to be displayed in the loading message.
     */
    public LoadingPanel(int lvl) {
        this.message = "Level "+ lvl +"!";
    }

    /**
     * Starts the loading animation.
     * The animation includes fading in and out of the loading message and displaying a start message.
     */
    public void startAnimation() {
        if (!started) {
            timer = new Timer(10, new ActionListener() {
                private final long startTime = System.currentTimeMillis();

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

    /**
     * Draws the loading animation on the specified Graphics2D object.
     *
     * @param g2d The Graphics2D object to render on.
     */
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, alpha));
        g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g2d.setFont(RETRO_FONT.deriveFont(Font.PLAIN,70f));

        Font font = RETRO_FONT.deriveFont(Font.PLAIN, 70f);
        g2d.setFont(font);

        FontMetrics metrics = g2d.getFontMetrics(font);
        int textWidth = metrics.stringWidth(message);

        int x = (SCREEN_WIDTH - textWidth) / 2;
        int y = (SCREEN_HEIGHT) / 2;

        drawBorder(g2d,x,y,4,message);

        g2d.setColor(Color.ORANGE);
        g2d.drawString(message, x, y);
    }
}
