package it.petrillo.jbomberman.model;

public abstract class GameObject extends GameEntity {

    protected boolean isSolid, isDestroyable;
    public GameObject(int x, int y, boolean visible) {
        super(x, y, visible);
    }
}
