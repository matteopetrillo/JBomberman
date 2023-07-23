package it.petrillo.jbomberman.model;

public abstract class GameObject extends GameEntity {

    protected boolean isSolid, isDestroyable, isExploding;
    public GameObject(int x, int y, boolean visible) {
        super(x, y);
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    public boolean isDestroyable() {
        return isDestroyable;
    }

    public void setDestroyable(boolean destroyable) {
        isDestroyable = destroyable;
    }

    public void setExploding(boolean exploding) {
        isExploding = exploding;
    }

    public boolean isExploding() {
        return isExploding;
    }
}
