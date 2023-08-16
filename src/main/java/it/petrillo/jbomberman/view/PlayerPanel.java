package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.controller.AudioManager;
import it.petrillo.jbomberman.model.GameStateListener;
import it.petrillo.jbomberman.util.CustomObserver;
import it.petrillo.jbomberman.util.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import static it.petrillo.jbomberman.util.GameSettings.*;

public class PlayerPanel extends JPanel implements CustomObserver {

    private String timerText = "01:30";
    private int score, win, lose;
    private int playerHealth = 5;
    private int bombs = 1;
    private Timer timer;
    private long countdownDuration = 90 * 1000;
    private final BufferedImage playerUI = getImg("/PlayerUI.png");
    private BufferedImage avatarImg;
    private String nickname;
    private GameStateListener gameStateListener;
    public PlayerPanel() {
        setDoubleBuffered(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH,150));
    }

    public void startTimer() {
        this.timer = new Timer(1000, e -> {
            countdownDuration -= 1000;
            if (countdownDuration < 0) {
                countdownDuration = 0;
                this.timer.stop();
                gameStateListener.onLosing();
            }
            updateTimer();
            repaint();
        });
        this.timer.start();
    }

    public void resetTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        countdownDuration = 90 * 1000;
        timerText = "01:30";
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(playerUI,0,0,SCREEN_WIDTH,150,null);
        g2d.setColor(Color.BLACK);
        g2d.setFont(RETRO_FONT.deriveFont(Font.PLAIN,60f));
        drawTimer(g2d);
        drawScore(g2d);
        g2d.setFont(RETRO_FONT.deriveFont(Font.PLAIN,70f));
        drawBorder(g2d,120,53,4,String.valueOf(playerHealth));
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(playerHealth),120,53);
        drawBorder(g2d,120,133,4,String.valueOf(bombs));
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(bombs),120,133);
        g2d.setFont(RETRO_FONT.deriveFont(Font.PLAIN,50));
        drawPlayerInfo(g2d);

    }

    private void updateTimer() {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        timerText = sdf.format(new Date(countdownDuration));
    }

    private void drawPlayerInfo(Graphics2D g2d) {
        drawBorder(g2d,525,50,3,nickname);
        g2d.setColor(Color.WHITE);
        g2d.drawString(nickname,525,50);
        g2d.drawImage(avatarImg,383,3,138,138,null);
        String winRecord = "Win: "+win;
        String loseRecord = "Lose: "+lose;
        g2d.setFont(RETRO_FONT.deriveFont(Font.PLAIN,35));
        drawBorder(g2d,555,90,2,winRecord);
        g2d.setColor(Color.WHITE);
        g2d.drawString(winRecord,555,90);
        drawBorder(g2d,555,120,2,loseRecord);
        g2d.setColor(Color.WHITE);
        g2d.drawString(loseRecord,555,120);
    }

    private void drawScore(Graphics2D g2d) {
        String scoreText = Integer.toString(score);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(scoreText);
        int textX = 970 - textWidth;
        int textY = 133;
        drawBorder(g2d,textX,textY,4,scoreText);
        g2d.setColor(Color.WHITE);
        g2d.drawString(scoreText, textX, textY);
    }

    private void drawTimer(Graphics2D g2d) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(timerText);
        int textX = 970 - textWidth;
        int textY = 53;
        drawBorder(g2d,textX,textY,4,timerText);
        g2d.setColor(Color.WHITE);
        g2d.drawString(timerText, textX, textY);
    }

    private void drawBorder(Graphics2D g2d, int x, int y, int amount,String message) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(message, x - amount, y - amount);
        g2d.drawString(message, x + amount, y - amount);
        g2d.drawString(message, x - amount, y + amount);
        g2d.drawString(message, x + amount, y + amount);
    }
    @Override
    public void update(NotificationType notificationType, Object arg) {

        switch (notificationType) {
            case SCORE_UPDATE -> {
                score = (Integer) arg;
                AudioManager.getAudioManagerInstance().play("/src/main/resources/enemy_defeated.wav",-16f);
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
            case FINISH_LEVEL -> resetTimer();
            case GAME_OVER -> timer.stop();
        }
    }
    public void uploadPlayerData(UserData playerData) {
        this.nickname = playerData.getNickname();
        this.avatarImg = getImg(playerData.getAvatarPath());
        this.win = playerData.getWin();
        this.lose = playerData.getLose();
        System.out.println("Player Loaded:" +
                "\n  Nick: "+nickname+
                "\n  Win: "+win+
                "\n  Lose: "+lose);
    }

    public void setGameStateListener(GameStateListener gameStateListener) {
        this.gameStateListener = gameStateListener;
    }

}
