package it.petrillo.jbomberman.view;

import it.petrillo.jbomberman.util.GameUtils;
import it.petrillo.jbomberman.util.Position;

import java.awt.*;

public class SpriteRenderer {

    private Rectangle playerSprite;
    private Position playerPosition;
    public void drawPlayer(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(playerPosition.getX(), playerPosition.getY(), GameUtils.Tile.SIZE.getValue(), GameUtils.Tile.SIZE.getValue());
    }

    public void setPlayerPosition(Position playerPosition) {
        this.playerPosition = playerPosition;
    }
}
