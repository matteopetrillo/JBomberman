package it.petrillo.jbomberman.view;


import it.petrillo.jbomberman.util.Settings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SpriteRenderer {

    private BufferedImage img;

    public SpriteRenderer() {
        importImg();
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/spritesheeet_bomberman_32x32.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void drawPlayer(Graphics2D g, int x,int y) {
        //BufferedImage subimg = img.getSubimage(0,2*32,32,32);
        //g.drawImage(subimg, x,y,(int) (Settings.TILE_SIZE*1.5),(int)(Settings.TILE_SIZE*1.5),null);
        g.setColor(Color.WHITE);
        g.fillRect(x,y,Settings.TILE_SIZE,Settings.TILE_SIZE);
    }

    public void drawEnemies(Graphics2D g, int x, int y) {
        g.setColor(Color.RED);
        g.fillRect(x,y, Settings.TILE_SIZE, Settings.TILE_SIZE);
    }

}
