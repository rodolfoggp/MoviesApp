package com.rodolfogusson.testepag.viewmodel.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rodolfogusson.testepag.infrastructure.data.persistence.dao.FavoriteDao
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate

class FavoritesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoritesViewModel
    private val favoritesRepository: FavoritesRepository = mock()

    private val favoritesLiveData = MutableLiveData<List<Movie>>()

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
        favoritesLiveData.value = moviesList
        whenever(favoritesRepository.getFavorites()).thenReturn(favoritesLiveData)
        viewModel = FavoritesViewModel(favoritesRepository)
    }

    @Test
    fun `favorites should getAllFavorites from repository`() {
        //WHEN
        //viewModel inits

        //THEN
        verify(favoritesRepository).getFavorites()

        val favorites = viewModel.favorites
            .test()
            .awaitValue()
            .value()

        assertEquals(moviesList, favorites)
    }
}