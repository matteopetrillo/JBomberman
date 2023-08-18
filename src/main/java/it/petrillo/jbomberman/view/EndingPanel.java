package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.util.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameConstants.*;
import static it.petrillo.jbomberman.util.UtilFunctions.drawBorder;
import static it.petrillo.jbomberman.util.UtilFunctions.getImg;

/**
 * The EndingPanel class represents a JPanel that contains visual elements
 * for the ending screen, including buttons and messages.
 */
public class EndingPanel extends JPanel {

    private final boolean playerWin;
    private final UserData userData;
    private final BufferedImage bgImg = getImg("/GUI/EndingScreenBG.png");
    private final JButton restartButton;

    /**
     * Constructs a new instance of the EndingPanel class with the specified victory status and user data.
     *
     * @param playerWin True if the player has won, false if the player has lost.
     * @param userData  The user data containing player information.
     */
    public EndingPanel(boolean playerWin, UserData userData) {
        this.playerWin = playerWin;
        this.userData = userData;
        setPreferredSize(new Dimension(640,480));
        setLayout(null);
        restartButton = new JButton();
        restartButton.setBounds(87,326,216,84);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        JButton quitButton = new JButton();
        quitButton.setBounds(345,326,216,84);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        add(quitButton);
        add(restartButton);
    }

    /**
     * Draw the ending screen elements, including background, messages, and buttons.
     *
     * @param g The Graphics object for rendering.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bgImg,0,0,null);
        g2d.setFont(RETRO_FONT.deriveFont(Font.PLAIN,80f));
        if (playerWin) {
            String winMessage = "YOU WON!";
            drawBorder(g2d,140,80,4, winMessage);
            g2d.setColor(Color.WHITE);
            g2d.drawString(winMessage,140,80);
        }
        else {
            String loseMessage = "YOU LOSE!";
            drawBorder(g2d,130,80,4, loseMessage);
            g2d.setColor(Color.WHITE);
            g2d.drawString(loseMessage, 130, 80);
        }
        String winRecord = "Win: "+userData.getWin();
        String loseRecord = "Lose: "+userData.getLose();
        g2d.setFont(RETRO_FONT.deriveFont(Font.PLAIN,50));
        drawBorder(g2d,242,190,3,winRecord);
        g2d.setColor(Color.WHITE);
        g2d.drawString(winRecord,242,190);
        drawBorder(g2d,230,240,3,loseRecord);
        g2d.setColor(Color.WHITE);
        g2d.drawString(loseRecord,230,240);

    }

    /**
     * Gets the restart button from the EndingPanel.
     *
     * @return The restart button.
     */
    public JButton getRestartButton() {
        return restartButton;
    }
}
