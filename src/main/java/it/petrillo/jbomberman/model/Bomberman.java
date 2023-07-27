package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.util.GameUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class Bomberman extends GameCharacter {

    private static Bomberman bombermanInstance;
    String playerName;

    private Bomberman() {
        super(0, 0);
        entityScale = 3.5d;
        xCollisionOffset = (int) (11*entityScale);
        yCollisionOffset = (int) (23*entityScale);
        entitySpeed = 4;
        collisionBox.setLocation(super.x+ xCollisionOffset, super.y+ yCollisionOffset);
        collisionBox.setSize((int) (9*entityScale), (int) (5*entityScale));
        animationSpeed = 13;
        health = 5;
        movingDirection = Direction.DOWN;
        loadSprites("/spritesheeet_bomberman_32x32.png");
    }

    @Override
    public void draw(Graphics2D g) {
        if(visible) {
            if (health <= 0)
                g.drawImage(spriteAnimation[4][animationIndex],
                        x, y, (int) (32 * entityScale), (int) (32 * entityScale), null);
            else {
            g.drawImage(spriteAnimation[getAniIndexByDirection()][animationIndex],
                    x, y, (int) (32 * entityScale), (int) (32 * entityScale), null);
            }
        }
    }

    @Override
    protected void loadSprites(String path) {
        spriteSheet = GameUtils.getImg(path);
        spriteAnimation = new BufferedImage[5][3];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(32*j,32*i, 32, 32);
            }
        }
    }

    @Override
    public void setX(int x) {
        super.setX((int) (x-6*SCALE));
    }

    @Override
    public void setY(int y) {
        super.setY((int) (y-14*SCALE));
    }


    private void updateAnimation() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            if (health <= 0) {
                animationIndex++;
                if (animationIndex >= 3)
                    visible = false;
            }
            else if (movingDown || movingLeft || movingUp || movingRight) {
                animationIndex++;
                if (animationIndex >= 3)
                    animationIndex = 0;
            }
            else
                animationIndex = 0;
        }
    }

    @Override
    public void updateStatus() {
        if (health > 0)
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
        health--;
        System.out.println(health);
        if (health <= 0) {
            animationIndex = 0;
            animationSpeed = 18;
        }
    }


    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public static Bomberman getPlayerInstance() {
        if(bombermanInstance == null) {
            bombermanInstance = new Bomberman();
        }
        return bombermanInstance;
    }

}
