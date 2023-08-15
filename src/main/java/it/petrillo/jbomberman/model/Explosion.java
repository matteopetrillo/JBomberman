package it.petrillo.jbomberman.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static it.petrillo.jbomberman.util.GameSettings.*;

/**
 * The Explosion class represents an explosion entity in the game.
 * It extends the GameEntity class and is responsible for rendering and animating explosion graphics.
 */
public class Explosion extends GameEntity {

    private final List<Direction> directions;
    private List<ExplosionCollisions> explosionCollisionsList = new ArrayList<>();

    /**
     * Constructs an Explosion instance with the specified initial position and explosion directions.
     *
     * @param x            The X-coordinate of the explosion's initial position.
     * @param y            The Y-coordinate of the explosion's initial position.
     * @param spritesPath  The path to the sprite sheet for the explosion animation.
     * @param directions   The list of directions in which the explosion expands.
     */
    public Explosion(int x, int y, String spritesPath, List<Direction> directions) {
        super(x, y);
        this.directions = directions;
        animationSpeed = 7;
        visible = true;
        initExplosionCollisions();
        loadSprites(spritesPath);
    }


    /**
     * Draws the explosion graphics onto the provided Graphics2D object.
     * Handles rendering explosion animation frames based on the specified directions.
     *
     * @param g The Graphics2D object to draw on.
     */
    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            if (directions.contains(Direction.UP))
                g.drawImage(spriteAnimation[1][animationIndex],x,y-TILE_SIZE,TILE_SIZE,TILE_SIZE,null);
            if (directions.contains(Direction.DOWN))
                g.drawImage(spriteAnimation[2][animationIndex],x,y+TILE_SIZE,TILE_SIZE,TILE_SIZE,null);
            if (directions.contains(Direction.RIGHT))
                g.drawImage(spriteAnimation[3][animationIndex],x+TILE_SIZE,y,TILE_SIZE,TILE_SIZE,null);
            if (directions.contains(Direction.LEFT))
                g.drawImage(spriteAnimation[4][animationIndex],x-TILE_SIZE,y,TILE_SIZE,TILE_SIZE,null);
            g.drawImage(spriteAnimation[0][animationIndex],x,y,TILE_SIZE,TILE_SIZE,null);
            g.draw(collisionBox);
        }
    }

    /**
     * Loads the explosion sprite images from the specified sprite sheet path.
     *
     * @param path The path to the sprite sheet for the explosion animation.
     */
    @Override
    public void loadSprites(String path) {
        spriteSheet = getImg(path);
        spriteAnimation = new BufferedImage[5][5];
        for (int i = 0; i < spriteAnimation.length; i++) {
            for (int j = 0; j < spriteAnimation[i].length; j++) {
                spriteAnimation[i][j] = spriteSheet.getSubimage(16*j, 16*i,16,16);
            }
        }
    }

    /**
     * Updates the explosion animation state.
     * Advances the animation index and manages the visibility of the explosion.
     * If the animation is complete, sets the explosion entity to be cleaned.
     */
    @Override
    public void update() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 5) {
                visible = false;
                animationIndex = 0;
                explosionCollisionsList.forEach(e -> e.setExpired(true));
                setToClean(true);
            }
        }
    }

    public void initExplosionCollisions() {
        explosionCollisionsList.add(new ExplosionCollisions(x, y));
        for (Direction d : directions) {
            switch (d) {
                case UP -> explosionCollisionsList.add(new ExplosionCollisions(x, y - TILE_SIZE));
                case DOWN -> explosionCollisionsList.add(new ExplosionCollisions(x, y + TILE_SIZE));
                case LEFT -> explosionCollisionsList.add(new ExplosionCollisions(x - TILE_SIZE, y));
                case RIGHT -> explosionCollisionsList.add(new ExplosionCollisions(x + TILE_SIZE, y));
            }
        }
    }

    public List<ExplosionCollisions> getExplosionCollisionsList() {
        return explosionCollisionsList;
    }

    public static class ExplosionCollisions extends Rectangle implements Collidable {

        boolean expired;
        private ExplosionCollisions(int x, int y) {
            super.x = x;
            super.y = y;
            super.width = TILE_SIZE;
            super.height = TILE_SIZE;

        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }

        public boolean isExpired() {
            return expired;
        }

        @Override
        public void onCollision(Collidable other) {
            if (other instanceof GameCharacter)
                ((GameCharacter) other).hitCharacter();
        }
    }

}
