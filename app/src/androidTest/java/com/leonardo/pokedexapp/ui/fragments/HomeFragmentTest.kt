package com.leonardo.pokedexapp.ui.fragments

import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import androidx.test.rule.ActivityTestRule
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.ui.MainActivity
import com.leonardo.pokedexapp.ui.adapters.HomeFragmentListAdapter
import org.hamcrest.CoreMatchers.not
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun setUp() {
        runOnUiThread {
            activityRule.activity.findNavController(R.id.nav_host).navigate(R.id.homeFragment)
        }
    }


    @Test
    fun testShowLoadingOnStart() {
        onView(withId(R.id.loading_gif)).check(matches(isDisplayed()))
    }


    @Test
    fun testNotVisibilityRecyclerView() {
        onView(withId(R.id.rvPokemonList)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testeShowRecyclerView(){
        Thread.sleep(4000)
        onView(withId(R.id.rvPokemonList)).check(matches(isDisplayed()))
    }


    @Test
    fun testClickPokemonNavigateDetails() {
        Thread.sleep(4000)
        onView(withId(R.id.rvPokemonList)).perform(
            actionOnItemAtPosition<HomeFragmentListAdapter.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.pokeball_loading)).check(matches(isDisplayed()))
    }



    @Test
    fun testClickBtnRandomNavigateDetails() {
        Thread.sleep(4000)
        onView(withId(R.id.btn_sort_pokemon)).perform(click())
        onView(withId(R.id.pokeball_loading)).check(matches(isDisplayed()))
    }


    @Test
    fun testClickBtnFilterPokemons() {
        Thread.sleep(4000)
        onView(withId(R.id.btn_filter_list)).perform(click())
        onView(withId(R.id.btn_choise_fire)).check(matches(isDisplayed()))
    }

    @Test
    fun testClickSearchPokemons() {
        Thread.sleep(4000)
        onView(withId(R.id.search_edit_text)).perform(click())

    }

    @Test
    fun testClickFavoritesPokemons() {
        Thread.sleep(4000)
        onView(withId(R.id.logo_pokedex)).perform(click())
        onView(withId(R.id.rv_pokemon_favorites)).check(matches(isDisplayed()))

    }


}

