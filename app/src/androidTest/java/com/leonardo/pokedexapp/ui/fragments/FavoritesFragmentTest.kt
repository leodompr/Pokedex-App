package com.leonardo.pokedexapp.ui.fragments

import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import androidx.test.rule.ActivityTestRule
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.ui.MainActivity
import com.leonardo.pokedexapp.ui.adapters.FavoritesPokemonAdapter
import com.leonardo.pokedexapp.ui.adapters.HomeFragmentListAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoritesFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun setUp() {
        Thread.sleep(8000)
        val action = HomeFragmentDirections.actionHomeFragmentToFavoritesFragment()
        UiThreadStatement.runOnUiThread {
            activityRule.activity.findNavController(R.id.nav_host).navigate(action)
        }
    }

    @Test
    fun testShowRvPokemonFavorites(){ //test after click on favorite button
        onView(withId(R.id.rv_pokemon_favorites)).check(matches(isDisplayed()))
    }

    @Test
    fun testClickPokemonNavigateDetails() {
        Thread.sleep(4000)
        onView(withId(R.id.rv_pokemon_favorites)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FavoritesPokemonAdapter.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        onView(withId(R.id.pokeball_loading)).check(matches(isDisplayed()))
    }


}