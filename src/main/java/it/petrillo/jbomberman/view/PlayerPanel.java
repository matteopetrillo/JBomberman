package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.util.CustomObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class PlayerPanel extends JPanel implements CustomObserver {

    private Font retroFont;
    private String timerText = "01:30";
    private int score;
    private int playerHealth = 5;
    private int bombs = 1;
    private Timer timer;
    private long countdownDuration = 90 * 1000;
    private BufferedImage playerUI;
    public PlayerPanel() {
        setDoubleBuffered(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH,150));
        setBackground(new Color(252, 189, 13));
        init();
    }

    private void init() {
        playerUI = getImg("/PlayerUI.jpg");
        try {
            InputStream is = getClass().getResourceAsStream("/RetroFont.ttf");
            retroFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
        timer = new Timer(1000, e -> {
            countdownDuration -= 1000;
            if (countdownDuration < 0) {
                countdownDuration = 0;
                timer.stop();
                GAME_STATE = GameState.GAME_OVER;
            }
            updateTimer();
            repaint();
        });
    }

    public void startTimer() {
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(playerUI,0,0,SCREEN_WIDTH,150,null);
        g2d.setColor(Color.BLACK);
        g2d.setFont(retroFont.deriveFont(Font.PLAIN,60f));
        drawTimer(g2d);
        drawScore(g2d);
        g2d.setFont(retroFont.deriveFont(Font.PLAIN,70f));
        g2d.drawString(String.valueOf(playerHealth),120,53);
        g2d.drawString(String.valueOf(bombs),120,133);
    }

    private void updateTimer() {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        timerText = sdf.format(new Date(countdownDuration));
    }

    private void drawScore(Graphics2D g2d) {
        String scoreText = Integer.toString(score);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(scoreText);
        int textX = 970 - textWidth;
        g2d.drawString(scoreText, textX, 133);
    }

    private void drawTimer(Graphics2D g2d) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(timerText);
        int textX = 970 - textWidth;
        g2d.drawString(timerText, textX, 53);
    }
    @Override
    public void update(NotificationType notificationType, Object arg) {
        switch (notificationType) {
            case SCORE_UPDATE -> {
                score = (Integer) arg;
                repaint();
            }
            case HEALTH_UPDATE -> {
                playerHealth = (Integer) arg;
                repaint();
            }
            case BOMB_UPDATE -> {
                bombs = (Integer) arg;
                repaint();
            }
        }
    }
}
