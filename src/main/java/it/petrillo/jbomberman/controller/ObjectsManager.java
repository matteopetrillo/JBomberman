package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.GameMap;

public class ObjectsManager {

    private static ObjectsManager objectsManagerInstance;
    private GameMap gameMap = GameMap.getInstance();

    private ObjectsManager() {}
    public void updateObjects() {
        gameMap.getObjects().stream()
                            .forEach(e -> e.updateStatus());
    }


    public static ObjectsManager getInstance() {
        if (objectsManagerInstance == null)
            objectsManagerInstance = new ObjectsManager();
        return objectsManagerInstance;
    }
}
