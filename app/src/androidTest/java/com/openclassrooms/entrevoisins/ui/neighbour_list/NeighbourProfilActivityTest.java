package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.test.rule.ActivityTestRule;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class NeighbourProfilActivityTest {

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mListNeighbourActivityActivityTestRule =
            new ActivityTestRule<>(ListNeighbourActivity.class);

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mService;
    private DummyNeighbourApiService mDummyNeighbourApiService;

    @Test
    /** test item clicable dans la liste de voisins **/
    public void onBindViewHolder() throws Exception {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.Nameprofile)).check(matches(isDisplayed()));

    }

    @Test
    /** test affichage de profil **/
    public void profilIsPopulated() throws Exception {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.Nameprofile)).check(matches(withText("Caroline")));
    }

    @Test
    /** test mise en favoris des profil **/
    public void FavorisListIsPopulated() throws Exception {
        //click sur un profil
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(actionOnItemAtPosition(0, click()));
        // click sur le bouton favoris dans la fiche profil
        onView(withId(R.id.favoris_Button)).perform(click());
        // retour en arriere
        pressBack();
        // bascule sur l'onglet favoris
        swipeLeft();
        // click sur le profil depuis l'onglet favoris
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(actionOnItemAtPosition(0, click()));
        // on verifie que le nom du profil favoris est bien le meme que celui choisi dans la liste des voisins
        onView(withId(R.id.Nameprofile)).check(matches(withText("Caroline")));
    }

    @Before
    public void setUp() {
        mActivity = mListNeighbourActivityActivityTestRule.getActivity();
        mService = DI.getNewInstanceApiService();

    }
}