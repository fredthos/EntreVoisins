package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        service.getFavNeighbours().clear();
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
        favNeighbours.add(service.getNeighbours().get(0));
        favNeighbours.add(service.getNeighbours().get(1));
        List<Neighbour> expectedFavNeighbours = service.getNeighbours().subList(0, 2);
        assertEquals(expectedFavNeighbours, service.getFavNeighbours());
    }

    @Test
    public void removeFavNeighbourWithSuccess() {
        List<Neighbour> favNeighbours = service.getFavNeighbours();
        favNeighbours.add(service.getNeighbours().get(0));
        favNeighbours.add(service.getNeighbours().get(1));
        Neighbour neighbourToRemove = service.getNeighbours().get(0);
        service.removeFavNeighbour(neighbourToRemove);
        assertFalse(service.getFavNeighbours().contains(neighbourToRemove));
    }

    @Test
    public void addFavNeighbourWithSuccess() {
        Neighbour neighbourToAdd = service.getNeighbours().get(0);
        service.addFavNeighbour(neighbourToAdd);
        assertTrue(service.getFavNeighbours().contains(neighbourToAdd));
    }

    @Test
    public void checkUserInListWithSuccess() {
        List<Neighbour> favNeighbours = service.getFavNeighbours();
        favNeighbours.add(service.getNeighbours().get(0));
        Neighbour neighbourToCheckInList = service.getNeighbours().get(0);
        service.checkUser(neighbourToCheckInList);
        assertTrue(service.checkUser(neighbourToCheckInList));
    }

    @Test
    public void checkUserNotInListWithSuccess() {
        List<Neighbour> favNeighbours = service.getFavNeighbours();
        favNeighbours.add(service.getNeighbours().get(0));
        Neighbour neighbourToCheckNotInList = service.getNeighbours().get(0);
        service.removeFavNeighbour(neighbourToCheckNotInList);
        assertTrue(!service.checkUser(neighbourToCheckNotInList));

    }
}
