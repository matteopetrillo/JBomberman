package it.petrillo.jbomberman.util;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameUtils {

    public enum SettingsPath {
        LEVEL_1("C:\\Coding\\Java\\JBomberman\\JBomberman\\src\\main\\resources\\level1_settings.json");

        private String value;
        SettingsPath(String value) {this.value = value;}
        public String getValue() {
            return value;
        }
    }


    public enum Direction {
        UP, DOWN, LEFT, RIGHT, IDLE
    }

    public enum NotificationType {
        DROP_BOMB
    }

    public enum SenderType {
        PLAYER, ENEMY_1, ENEMY_2, MAP
    }

    public static BufferedImage getImg(String path) {
        InputStream is = GameUtils.class.getResourceAsStream(path);
        try {
            if (is != null)
                return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}
