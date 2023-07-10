package it.petrillo.jbomberman.view;


import it.petrillo.jbomberman.util.Settings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SpriteRenderer {

    private BufferedImage img;
    private Map<Integer, BufferedImage> tilesImg = new HashMap<>();

    public SpriteRenderer() {
        img = importImg("/spritesheeet_bomberman_32x32.png");
        readTileSheet("/MapTileset_Level1.png");

    }

    private BufferedImage importImg(String path) {
        InputStream is = getClass().getResourceAsStream(path);
        try {
            return ImageIO.read(is);
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
        return null;
    }

    public Map<Integer, BufferedImage> getTilesImg() {
        return tilesImg;
    }

    public void drawPlayer(Graphics2D g, int x, int y, Shape s) {
        BufferedImage subimg = img.getSubimage(0,2*32,32,32);
        g.drawImage((Image) subimg, x,y, (int) (Settings.TILE_SIZE*1.5d),
                (int) (Settings.TILE_SIZE*1.5d),null);
        g.setColor(Color.RED);
        g.draw(s);
        /*
        g.setColor(Color.WHITE);
        g.fillRect(x,y, (int) (16*Settings.SCALE), (int) (24*Settings.SCALE));

         */
    }

    public void drawEnemies(Graphics2D g, int x, int y) {
        /*
        g.setColor(Color.RED);
        g.fillRect(x,y, Settings.TILE_SIZE, Settings.TILE_SIZE);

         */
    }

    private void readTileSheet(String path) {
        BufferedImage sheet = importImg(path);
        int width = sheet.getWidth();
        int tileSize = Settings.DEFAULT_TILE_SIZE;
        int index = 1;
        for (int i = 0; i < width/tileSize; i++) {
            BufferedImage subimg = sheet.getSubimage(i*tileSize,0, tileSize,tileSize);
            tilesImg.put(index, subimg);
            index++;
        }
    }
}
