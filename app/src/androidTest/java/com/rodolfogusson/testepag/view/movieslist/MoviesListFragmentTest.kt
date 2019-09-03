package com.rodolfogusson.testepag.view.movieslist

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.rodolfogusson.testepag.R
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class MoviesListFragmentTest {

    lateinit var scenario: FragmentScenario<MoviesListFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer<MoviesListFragment>()
    }

    @Test
    fun shouldContainARecyclerView() {
        onView(withId(R.id.testid)).check(matches(isDisplayed()))
    }
}