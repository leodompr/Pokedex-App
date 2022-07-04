package com.leonardo.pokedexapp.ui.fragments

import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import androidx.test.rule.ActivityTestRule
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.ui.MainActivity
import com.leonardo.pokedexapp.ui.adapters.PokemonByTypesAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PokemonsByTypesFragmentTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun setUp() {
        Thread.sleep(8000)
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsPokemonFragment("pikachu")
        runOnUiThread {
            activityRule.activity.findNavController(R.id.nav_host).navigate(action)
        }
    }


    @Test
    fun testClickPokemonNavigateDetails() {
        Thread.sleep(4000)
        onView(withId(R.id.rv_pokemon_by_types)).perform(
            actionOnItemAtPosition<PokemonByTypesAdapter.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.pokeball_loading)).check(matches(isDisplayed()))
    }

}