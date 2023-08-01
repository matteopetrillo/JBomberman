package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.Explosion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExplosionManager {

    private static ExplosionManager explosionManagerInstance;
    private static List<Explosion> explosionList = new ArrayList<>();

    public void updateExplosions() {
        if (!explosionList.isEmpty()) {
            explosionList.stream().forEach(e -> e.update());
            cleanObjects();
        }
    }

    public static void addExplosion(Explosion e) {
        explosionList.add(e);
    }

    public List<Explosion> getExplosionList() {
        return explosionList;
    }

    private void cleanObjects() {
        List<Explosion> destroyedObjects = explosionList.stream()
                .filter(e -> !e.isVisible())
                .collect(Collectors.toCollection(ArrayList::new));

        destroyedObjects.stream()
                .forEach(e -> explosionList.remove(e));
    }

    public static ExplosionManager getInstance() {
        if (explosionManagerInstance == null)
            explosionManagerInstance = new ExplosionManager();
        return explosionManagerInstance;
    }
}
