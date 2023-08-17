package it.petrillo.jbomberman.model;

import it.petrillo.jbomberman.model.characters.GameCharacter;
import it.petrillo.jbomberman.model.interfaces.Collidable;
import it.petrillo.jbomberman.util.Direction;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.List;

import static it.petrillo.jbomberman.util.GameConstants.*;
import static it.petrillo.jbomberman.util.UtilFunctions.getImg;

/**
 * The Explosion class represents an explosion entity in the game.
 * It extends the GameEntity class and is responsible for rendering and animating explosion graphics.
 */
public class Explosion extends GameEntity implements Collidable {

    private final List<Direction> directions;

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
        createCollisionBox();
        loadSprites(spritesPath);
    }

    private void createCollisionBox() {
        collisionBox = new Area(new Rectangle(x,y,TILE_SIZE,TILE_SIZE));
        for (Direction d : directions) {
            int xOffset = 0;
            int yOffset = 0;

            switch (d) {
                case UP -> yOffset = -TILE_SIZE;
                case DOWN -> yOffset = TILE_SIZE;
                case LEFT -> xOffset = -TILE_SIZE;
                case RIGHT -> xOffset = TILE_SIZE;
            }

            Rectangle box = new Rectangle(x + xOffset, y + yOffset, TILE_SIZE, TILE_SIZE);
            collisionBox.add(new Area(box));
        }
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
                if (spriteSheet != null) {
                    spriteAnimation[i][j] = spriteSheet.getSubimage(16*j, 16*i,16,16);
                }
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
                setToClean(true);
            }
        }
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof GameCharacter)
            ((GameCharacter)other).hitCharacter();
    }

}
