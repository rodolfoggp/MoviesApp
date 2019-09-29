package com.rodolfogusson.testepag.view.details

import com.nhaarman.mockitokotlin2.mock
import com.rodolfogusson.testepag.viewmodel.details.moviedetails.MovieDetailsViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class MovieDetailsActivityTest {
    private lateinit var activity: MovieDetailsActivity
    private lateinit var activityController: ActivityController<MovieDetailsActivity>
    private val viewModelMock: MovieDetailsViewModel = mock()
    private val id = 12345

    @Before
    fun setup() {
        activityController = Robolectric.buildActivity(MovieDetailsActivity::class.java)
        activityController.create()

/*        activity.setTestViewModel(viewModelMock)
        activity.id = id*/

        activityController.start()
    }

    private fun createActivity() {
        activity = activityController.create()
            .resume()
            .get()
    }

    @Test
    fun `activity should not be null`() {
        createActivity()
        assertNotNull(activity)
    }

    @Test
    fun `binding should not be null`() {
        createActivity()
        assertNotNull(activity.binding)
    }

    @Test
    fun `viewModel should be set`() {
        assertNotNull(activity.binding.viewModel)
    }

/*    @Test
    fun `id passed to vm should be correct`() {
        verify(viewModelMock).id = id
    }*/

/*    @Test
    fun `it should display movie image`() {
        assertNotNull(activity.detailsImage.)
    }*/
}