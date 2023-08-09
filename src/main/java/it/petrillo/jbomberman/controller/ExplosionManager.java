package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.Explosion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExplosionManager {

    private static ExplosionManager explosionManagerInstance;
    private final List<Explosion> explosionList = new ArrayList<>();

    public void updateExplosions() {
        clean();
        if (!explosionList.isEmpty()) {
            explosionList.forEach(Explosion::update);
        }
    }

    public void addExplosion(Explosion e) {
        explosionList.add(e);
    }

    public List<Explosion> getExplosionList() {
        return explosionList;
    }

    private void clean() {
        List<Explosion> toClean = explosionList.stream()
                .filter(e -> !e.isVisible())
                .collect(Collectors.toCollection(ArrayList::new));

        toClean.stream()
                .forEach(e -> explosionList.remove(e));
    }

    public static ExplosionManager getInstance() {
        if (explosionManagerInstance == null)
            explosionManagerInstance = new ExplosionManager();
        return explosionManagerInstance;
    }
}
