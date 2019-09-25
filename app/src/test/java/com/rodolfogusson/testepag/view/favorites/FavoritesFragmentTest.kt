package com.rodolfogusson.testepag.view.favorites

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import com.nhaarman.mockitokotlin2.mock
import com.rodolfogusson.testepag.viewmodel.favorites.FavoritesViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FavoritesFragmentTest {

    private lateinit var scenario: FragmentScenario<FavoritesFragment>
    private val viewModelMock: FavoritesViewModel = mock()

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onFragment {
            it.viewModel = viewModelMock
        }
    }

    @Test
    fun `viewModel should not be null`() {
        //GIVEN
        scenario = launchFragmentInContainer()

        //WHEN
        scenario.moveToState(Lifecycle.State.RESUMED)

        //THEN
        scenario.onFragment {
            assertNotNull(it.viewModel)
        }
    }

    @Test
    fun `recyclerView adapter should be set`() {
        scenario.onFragment {
            assertNotNull(it.adapter)
            assertEquals(it.adapter, it.recyclerView.adapter)
        }
    }

    @Test
    fun `adapter's clickListener should be set`() {
        scenario.onFragment {
            assertNotNull(it.adapter.clickListener)
        }
    }
}