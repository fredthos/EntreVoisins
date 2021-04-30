package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getFavNeighboursWithSuccess() {
        List<Neighbour> favNeighbours = service.getFavNeighbours();
        List<Neighbour> favExpectedNeighbours = new ArrayList<>();
        assertThat(favNeighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(favExpectedNeighbours.toArray()));

    }

    @Test
    public void removeFavNeighbourWithSuccess() {
        Neighbour neighbourToRemove = service.getNeighbours().get(0);
        service.removeFavNeighbour(neighbourToRemove);
        assertFalse(service.getFavNeighbours().contains(neighbourToRemove));
    }

    @Test
    public void addFavNeighbourWithSuccess(){
        Neighbour neighbourToAdd = service.getNeighbours().get(0);
        service.addFavNeighbour(neighbourToAdd);
        assertTrue(service.getFavNeighbours().contains(neighbourToAdd));

    }

    @Test
    public void checkUserWithSuccess() {
        Neighbour neighbourToCheck = service.getNeighbours().get(0);
        service.checkUser(neighbourToCheck);
        assert(service.getFavNeighbours().contains(neighbourToCheck));
    }
}
