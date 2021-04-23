package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> favNeighbours = new ArrayList<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     *
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public List<Neighbour> getFavNeighbours() {
        return favNeighbours;
    }

    @Override
    public void removeFavNeighbour(Neighbour neighbour) {
        favNeighbours.remove(neighbour);
    }

    @Override
    public void addFavNeighbour(Neighbour neighbour) {
        favNeighbours.add(neighbour);
    }

}
