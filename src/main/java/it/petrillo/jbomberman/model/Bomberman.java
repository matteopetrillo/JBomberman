package it.petrillo.jbomberman.model;

import java.awt.*;
import java.awt.image.BufferedImage;

import static it.petrillo.jbomberman.util.GameUtils.*;

public class Bomberman extends GameCharacter implements Movable,Animatable {

    private static Bomberman bombermanInstance;
    private int bombBackpack = 1;
    private int bombReleased, score;
    String playerName;

    private Bomberman() {
        super(0, 0);
        entityScale = 3.5d;
        xCollisionOffset = (int) (11*entityScale);
        yCollisionOffset = (int) (23*entityScale);
        characterSpeed = 4;
        collisionBox.setLocation(super.x+ xCollisionOffset, super.y+ yCollisionOffset);
        collisionBox.setSize((int) (9*entityScale), (int) (5*entityScale));
        animationSpeed = 13;
        health = 5;
        visible = true;
        movingDirection = Direction.DOWN;
        loadSprites("/spritesheeet_bomberman_32x32.png");
    }

    @Override
    public void draw(Graphics2D g) {
        if(visible) {
            if (health <= 0)
                g.drawImage(spriteAnimation[4][animationIndex],
                        x, y, (int) (32 * entityScale), (int) (32 * entityScale), null);
            else if (hittedTimer > 0){
                BufferedImage img = spriteAnimation[getAniIndexByDirection()][animationIndex];
                g.drawImage(img, x, y, (int) (32 * entityScale), (int) (32 * entityScale), null);
                if (hittedTimer%3 == 0) {
                    int width = img.getWidth();
                    int height = img.getHeight();
                    BufferedImage filteredImg = new BufferedImage(width,height,img.getType());
                    Graphics2D g2d = filteredImg.createGraphics();
                    g2d.drawImage(img,0,0,null);
                    g2d.dispose();
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            int rgb = filteredImg.getRGB(x, y);
                            int alpha = (rgb >> 24) & 0xFF;
                            if (alpha > 0) {
                                Color color = new Color(rgb);
                                Color white = new Color(255, 255, 255, alpha);
                                filteredImg.setRGB(x, y, white.getRGB());
                            }
                        }
                    }
                    g.drawImage(filteredImg, x, y, (int) (32 * entityScale), (int) (32 * entityScale), null);
                }
            }
            else {
                g.drawImage(spriteAnimation[getAniIndexByDirection()][animationIndex],
                        x, y, (int) (32 * entityScale), (int) (32 * entityScale), null);
            }
        }
    }

    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
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

    @Override
    public int getX() {
        return collisionBox.x;
    }

    @Override
    public int getY() {
        return collisionBox.y;
    }

    @Override
    public void update() {
        if (hittedTimer > 0)
            hittedTimer--;
        if (health > 0)
            updatePosition();
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
    public void updatePosition() {
        xSpeed = 0;
        ySpeed = 0;

        if (movingUp) {
            ySpeed = -characterSpeed;
            movingDirection = Direction.UP;
        }
        else if (movingDown) {
            ySpeed = characterSpeed;
            movingDirection = Direction.DOWN;
        }
        else if (movingLeft) {
            xSpeed = -characterSpeed;
            movingDirection = Direction.LEFT;
        }
        else if (movingRight) {
            xSpeed = characterSpeed;
            movingDirection = Direction.RIGHT;
        }

        if(collisionListener.canMoveThere(xSpeed, ySpeed,collisionBox)) {
            super.x += xSpeed;
            super.y += ySpeed;
            collisionBox.setLocation(super.x+xCollisionOffset, super.y+yCollisionOffset);
        }
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy)
            hitCharacter();
    }

    public void healCharacter() {
        if (health < 5) {
            health++;
            notifyObservers(NotificationType.HEALTH_UPDATE,health);
        }
    }

    public void increaseBombBackpack() {
        bombBackpack++;
        notifyObservers(NotificationType.BOMB_UPDATE,getBombAvailable());
    }
    public int getBombAvailable() {
        return bombBackpack-bombReleased;
    }
    @Override
    public void hitCharacter() {
        if (hittedTimer <= 0 && health > 0) {
            health--;
            notifyObservers(NotificationType.HEALTH_UPDATE,health);
            hittedTimer = 60;
            hitted = false;
        }
        else if (health <= 0) {
            animationIndex = 0;
            animationSpeed = 18;
        }
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void advanceLevel() {
        notifyObservers(NotificationType.FINISH_LEVEL,null);
    }

    public int getBombBackpack() {
        return bombBackpack;
    }
    public boolean canDropBomb() {
        return getBombAvailable()>0;
    }

    public void alterBombReleased(int k) {
        bombReleased += k;
        notifyObservers(NotificationType.BOMB_UPDATE,getBombAvailable());
    }

    public void alterScore(int k) {
        score += k;
        notifyObservers(NotificationType.SCORE_UPDATE,score);
    }

    public static Bomberman getPlayerInstance() {
        if(bombermanInstance == null) {
            bombermanInstance = new Bomberman();
        }
        return bombermanInstance;
    }

}
