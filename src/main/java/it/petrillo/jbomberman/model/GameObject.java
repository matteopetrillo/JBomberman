package it.petrillo.jbomberman.model;

/**
 * The GameObject class represents a game entity with properties related to solidity and destroyability.
 * It extends the GameEntity class and provides methods to check and set these properties.
 */
public abstract class GameObject extends GameEntity {

    protected boolean isSolid, isDestroyable, toClean;

    /**
     * Creates a new GameObject instance with the specified initial coordinates.
     *
     * @param x The initial x-coordinate of the game object.
     * @param y The initial y-coordinate of the game object.
     */
    public GameObject(int x, int y) {
        super(x, y);
    }

    /**
     * Checks whether the game object is solid (blocks movement) or not.
     *
     * @return True if the game object is solid, otherwise false.
     */
    public boolean isSolid() {
        return isSolid;
    }

    /**
     * Sets the solidity status of the game object.
     *
     * @param solid The new solidity status to be set.
     */
    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    /**
     * Checks whether the game object is destroyable or not.
     *
     * @return True if the game object is destroyable, otherwise false.
     */
    public boolean isDestroyable() {
        return isDestroyable;
    }

    /**
     * Sets the destroyability status of the game object.
     *
     * @param destroyable The new destroyability status to be set.
     */
    public void setDestroyable(boolean destroyable) {
        isDestroyable = destroyable;
    }

}
