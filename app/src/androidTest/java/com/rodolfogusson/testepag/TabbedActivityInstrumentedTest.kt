package com.rodolfogusson.testepag

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TabbedActivityInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<TabbedActivity>
            = ActivityTestRule(TabbedActivity::class.java)

    @Test
    fun whenMoviesListTabIsClicked_MoviesListFragmentShouldBeShown() {
        onView(withId(R.id.movies_tab)).perform(click())
        onView(withId(R.id.movies_list_fragment)).check(matches(isDisplayed()))
    }
}
