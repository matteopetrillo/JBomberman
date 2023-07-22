package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.GameUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class Bomberman extends GameCharacter {

    private static Bomberman bombermanInstance;
    String playerName;
    private int health = 5;

    private Bomberman(int x, int y, boolean visibility) {
        super(x, y, visibility);
        entityScale = 3.5d;
        xCollisionOffset = (int) (11*entityScale);
        yCollisionOffset = (int) (23*entityScale);
        entitySpeed = 4;
        collisionBox.setLocation(super.x+ xCollisionOffset, super.y+ yCollisionOffset);
        collisionBox.setSize((int) (9*entityScale), (int) (5*entityScale));
        animationSpeed = 13;
        movingDirection = Direction.DOWN;
        loadSprites("/spritesheeet_bomberman_32x32.png");
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(spriteAnimation[getAniIndexByDirection()][animationIndex],
                x,y, (int) (32*entityScale), (int) (32*entityScale),null);
        //g.setColor(Color.RED);
        //g.draw(collisionBox);
    }

    @Override
    protected void loadSprites(String path) {
        this.spriteSheet = GameUtils.getImg(path);
        this.spriteAnimation = new BufferedImage[4][3];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(32*j,32*i, 32, 32);
            }

        }
    }

    private void updateAnimation() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            if (movingDown || movingLeft || movingUp || movingRight) {
                animationIndex++;
                if (animationIndex >= 3)
                    animationIndex = 0;
            }
            else
                animationIndex = 0;
        }
    }

    public void updateStatus() {
        updatePosition();
        updateAnimation();

    }
    public void updatePosition() {
        xSpeed = 0;
        ySpeed = 0;

        if (movingUp) {
            ySpeed = -entitySpeed;
            movingDirection = Direction.UP;
        }
        else if (movingDown) {
            ySpeed = entitySpeed;
            movingDirection = Direction.DOWN;
        }
        else if (movingLeft) {
            xSpeed = -entitySpeed;
            movingDirection = Direction.LEFT;
        }
        else if (movingRight) {
            xSpeed = entitySpeed;
            movingDirection = Direction.RIGHT;
        }


        if(collisionListener != null && collisionListener.canMoveThere(xSpeed, ySpeed,collisionBox)) {
            super.x += xSpeed;
            super.y += ySpeed;
            collisionBox.setLocation(super.x+xCollisionOffset, super.y+yCollisionOffset);
        }
    }

    public void dropBomb() {
        notifyObservers((NotificationType.DROP_BOMB), this);
    }

    public void hitPlayer() {
        System.out.println(health);
        health--;
        System.out.println(health);
    }


    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public static Bomberman getPlayerInstance() {
        if(bombermanInstance == null) {
            bombermanInstance = new Bomberman((int) (2*TILE_SIZE-TILE_SIZE/2), (int) (1*TILE_SIZE-TILE_SIZE/2), true);
        }
        return bombermanInstance;
    }

}
