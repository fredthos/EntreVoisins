package com.openclassrooms.entrevoisins.neighbour_list;

import android.app.Service;
import android.content.Intent;
import android.os.Parcelable;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourProfilActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.security.Provider;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearGlobalAssertions;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService service;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        service = DI.getNewInstanceApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT ));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * Ouverture du detail au moment de al selection d'un voisin dans la liste.
     */
    @Test
    public void myNeighbourList_onClickItem_shouldOpenNeighbourProfilActivity() {
        //Resultat : ouverture du detail
        //Click sur le voisin
        onView(ViewMatchers.withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Aprés: verification de l'affichage du voisin
        onView(ViewMatchers.withId(R.id.activity_profil_details)).check(matches(isDisplayed()));
    }

    /**
     * Verification du nom du NeighbourProfilActivity est le meme que le voisin selectionné
     */
    @Test
    public void activityProfilName_onNeighbourprofilactivity_isCorrect(){
        //Resultat : Affichage du bon prenom dans le profil
        //Ouverture du profil
        onView(ViewMatchers.withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        //Verification prenom du profile = prenom du voisin
        onView(ViewMatchers.withId(R.id.TBneighbourName)).check(matches(withText("Caroline")));
    }
    /**
     * verification de suppression d'un neighbour dans la liste des favoris qui a ete peuplé auparavent
     */
    @Test
    public void addNeighbour_inFavNeighboursList_andDeleteNeighbourInList_isCorrect() {
        //Ajout de favoris dans la liste des favoris
        //Ouverture des neighbour et ajout dans liste des favoris
        onView(ViewMatchers.withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(ViewMatchers.withId(R.id.favoris_Button)).perform(click());
        pressBack();

        onView(ViewMatchers.withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(5,click()));
        onView(ViewMatchers.withId(R.id.favoris_Button)).perform(click());
        pressBack();

        onView(ViewMatchers.withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(8,click()));
        onView(ViewMatchers.withId(R.id.favoris_Button)).perform(click());
        pressBack();

        //Selection de l'onglet favoris
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(swipeLeft());

        //Verfication du nombre de neigbour ajouter (3)
        onView(ViewMatchers.withId(R.id.list_fav_neighbours)).check(withItemCount(3));
        //Suppresion d'un neighbour
        onView(ViewMatchers.withId(R.id.list_fav_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        //verification du nombre de neighbour dans la liste restant -1
        onView(ViewMatchers.withId(R.id.list_fav_neighbours)).check(withItemCount(2));
    }

    /**
     * Verification que l'onglet Favoris affiche que les voisins marqués favoris
     */
    @Test
    public void inFavorisList_checkNeighbourTaggedIsFavoris_isCorrect(){
        //Remise a zero de la liste de favoris
        service.getNeighbours().forEach(neighbour -> service.removeFavNeighbour(neighbour));
        //Verifier que la liste de favori est vide
        onView(ViewMatchers.withId(R.id.list_fav_neighbours))
                .check(matches(hasMinimumChildCount(0)));
        //Ajout de favoris dans la liste des favoris
        //Ouverture des neighbour et ajout dans liste des favoris
        onView(ViewMatchers.withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(ViewMatchers.withId(R.id.favoris_Button)).perform(click());
        pressBack();

        onView(ViewMatchers.withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(4,click()));
        onView(ViewMatchers.withId(R.id.favoris_Button)).perform(click());
        pressBack();

        onView(ViewMatchers.withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(9,click()));
        onView(ViewMatchers.withId(R.id.favoris_Button)).perform(click());
        pressBack();

        //Selection de l'onglet favoris
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(swipeLeft());

        //Verfication du nombre de neigbour ajouter (3)
        onView(ViewMatchers.withId(R.id.list_fav_neighbours)).check(withItemCount(3));
    }
}