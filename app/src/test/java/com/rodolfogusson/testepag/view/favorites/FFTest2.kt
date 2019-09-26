package com.rodolfogusson.testepag.view.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.*
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.viewmodel.favorites.FavoritesViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.threeten.bp.LocalDate

@RunWith(RobolectricTestRunner::class)
class FFTest2 : AutoCloseKoinTest() {

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

    private val viewModel: FavoritesViewModel = mock()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        loadKoinModules(module(override = true) {
            viewModel {
                viewModel
            }
        })
        /*val testModule = module {
            viewModel {
                viewModel
            }
        }
        startKoin {
            modules(testModule)
        }*/
    }

    @Test
    fun `viewModel should not be null`() {
        //GIVEN
        val liveDataMock: LiveData<List<Movie>> = mock()
        whenever(viewModel.favorites).thenReturn(liveDataMock)

        //WHEN
        val scenario = launchFragmentInContainer<FavoritesFragment>()

        //THEN
        scenario.onFragment {
            Assert.assertNotNull(it.vm)
        }
    }

    @Test
    fun `favorites from viewModel should be observed`() {
        //GIVEN
        val liveDataMock: LiveData<List<Movie>> = mock()
        whenever(viewModel.favorites).thenReturn(liveDataMock)

        //WHEN
        launchFragmentInContainer<FavoritesFragment>()

        //THEN
        verify(liveDataMock).observe(any(), any())
    }
}