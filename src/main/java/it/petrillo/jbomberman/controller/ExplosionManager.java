package it.petrillo.jbomberman.controller;

import it.petrillo.jbomberman.model.Explosion;
import it.petrillo.jbomberman.model.GameEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The ExplosionManager class manages the explosions in the game, including their updates and cleanup.
 */
public class  ExplosionManager {

    private static ExplosionManager explosionManagerInstance;
    private final List<Explosion> explosionList = new ArrayList<>();

    private ExplosionManager() {}

    /**
     * Updates all explosions, removing those that are no longer visible.
     */
    public void updateExplosions() {
        clean();
        if (!explosionList.isEmpty()) {
            explosionList.forEach(Explosion::update);
        }
    }

    /**
     * Adds an explosion to the manager's list of explosions.
     *
     * @param e The explosion to be added.
     */
    public void addExplosion(Explosion e) {
        explosionList.add(e);
    }

    /**
     * Retrieves the list of active explosions.
     *
     * @return The list of active explosions.
     */
    public List<Explosion> getExplosionList() {
        return explosionList;
    }

    /**
     * Cleans up explosions that are no longer visible from the explosion list.
     */
    private void clean() {
        List<Explosion> toClean = explosionList.stream()
                .filter(GameEntity::isToClean)
                .collect(Collectors.toCollection(ArrayList::new));

        toClean.forEach(explosionList::remove);
    }

    /**
     * Retrieves the singleton instance of the ExplosionManager.
     *
     * @return The ExplosionManager instance.
     */
    public static ExplosionManager getInstance() {
        if (explosionManagerInstance == null)
            explosionManagerInstance = new ExplosionManager();
        return explosionManagerInstance;
    }
}
