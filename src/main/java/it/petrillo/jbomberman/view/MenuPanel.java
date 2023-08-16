package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.controller.GameManager;
import it.petrillo.jbomberman.util.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MenuPanel extends JPanel {
    private final BufferedImage backgroundImage = GameSettings.getImg("/GameMenuBg.png");
    private final JTextField nickField = new JTextField();
    private String chosenAvatarPath;
    private JButton playButton, quitButton;
    private ArrayList<AvatarButton> avatarsButtonList = new ArrayList<>();
    public MenuPanel() {
        setPreferredSize(new Dimension(backgroundImage.getWidth(),backgroundImage.getHeight()));
        setLayout(null);

        nickField.setBounds(395,338,250,40);
        nickField.setFont(new Font("Arial",Font.BOLD,22));
        nickField.setHorizontalAlignment(SwingConstants.CENTER);

        avatarsButtonList.add(new AvatarButton("/Avatars/Avatar1_Normal.png", "/Avatars/Avatar1_Selected.png",105,420));
        avatarsButtonList.add(new AvatarButton("/Avatars/Avatar2_Normal.png", "/Avatars/Avatar2_Selected.png",255,420));
        avatarsButtonList.add(new AvatarButton("/Avatars/Avatar3_Normal.png", "/Avatars/Avatar3_Selected.png",405,420));
        avatarsButtonList.add(new AvatarButton("/Avatars/Avatar4_Normal.png", "/Avatars/Avatar4_Selected.png",555,420));
        for (AvatarButton buttons : avatarsButtonList) {
            buttons.setBounds(buttons.getX(), buttons.getY(),140,140);
            buttons.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    buttons.setHover(true);
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    buttons.setHover(false);
                    setCursor(Cursor.getDefaultCursor());
                    repaint();
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    chosenAvatarPath = buttons.getAvatarPath();
                    avatarsButtonList.stream().filter(AvatarButton::isSelected).forEach(b -> b.setSelected(false));
                    buttons.setSelected(true);
                    repaint();
                }
            });
            add(buttons);
        }

        playButton = new JButton();
        playButton.setBounds(130,630,210,95);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        quitButton = new JButton();
        quitButton.setBounds(465,630,210,95);
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
        add(playButton);
        add(nickField);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backgroundImage,0,0,null);
        avatarsButtonList.forEach(b -> b.draw(g2d));

    }

    public JButton getPlayButton() {
        return playButton;
    }
    public String getChosenAvatarPath() {
        return chosenAvatarPath;
    }
    public String getNickname() {
        return nickField.getText();
    }

}
