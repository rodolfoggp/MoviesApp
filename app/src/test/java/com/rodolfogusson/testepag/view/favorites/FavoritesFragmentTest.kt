package com.rodolfogusson.testepag.view.favorites

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.nhaarman.mockitokotlin2.*
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.viewmodel.favorites.FavoritesViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.threeten.bp.LocalDate

//@RunWith(RobolectricTestRunner::class)
class FavoritesFragmentTest: AutoCloseKoinTest() {

    private lateinit var scenario: FragmentScenario<FavoritesFragment>
    val viewModel: FavoritesViewModel by inject()
    val viewModelMock: FavoritesViewModel = mock {
        on { favorites } doReturn favoritesLiveData
    }
    private val favoritesLiveData = MutableLiveData<List<Movie>>().apply { value = moviesList }
    private val moviesList = listOf(
        Movie(
            1,
            "Filme 1",
            "Descrição 1",
            LocalDate.parse("2019-05-12"),
            "/wF6SNPcUrTKFA4fOFfukm7zQ3ob.jpg",
            6.4,
            10
        ),
        Movie(
            2,
            "Filme 2",
            "Descrição 2",
            LocalDate.parse("2019-08-26"),
            "/foEOVg4HQl2VzKzTh27CAHmXyg.jpg",
            7.9,
            17
        ),
        Movie(
            3,
            "Filme 3",
            "Descrição 2",
            LocalDate.parse("2019-07-22"),
            "/foEOVg4HQl2VzKzTh27CAHmXyg.jpg",
            8.0,
            19
        )
    )



    @Before
    fun setup() {

        loadKoinModules(module {
            viewModel(override = true) { viewModelMock }
        })

/*        declareMock<FavoritesViewModel> {
            given(favorites).willReturn(favoritesLiveData)
        }*/
        scenario = launchFragmentInContainer()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun `viewModel should not be null`() {
        //GIVEN
        scenario = launchFragmentInContainer()

        //WHEN
        scenario.moveToState(Lifecycle.State.RESUMED)

        //THEN
        scenario.onFragment {
            assertNotNull(it.vm)
        }
    }

    @Test
    fun `binding is correctly done`() {
        scenario.onFragment {
            assertNotNull(it.binding)
            assertNotNull(it.binding.viewModel)
        }
    }

    @Test
    fun `recyclerView should be correctly set`() {
        scenario.onFragment {
            assertNotNull(it.adapter)
            assertEquals(it.adapter, it.recyclerView.adapter)
            assert(it.recyclerView.layoutManager is LinearLayoutManager)
        }
    }

    @Test
    fun `adapter's clickListener should be set`() {
        scenario.onFragment {
            assertNotNull(it.adapter.clickListener)
        }
    }

    @Test
    fun `favorites from viewModel is observed`() {
        scenario.onFragment {
            verify(viewModel).favorites.observe(any(), any())
        }
    }
}