package com.rodolfogusson.testepag.viewmodel.details.favoritedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.rodolfogusson.testepag.TestHelper
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteDetailsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoriteDetailsViewModel

    private val testHelper = TestHelper()
    private val movie = testHelper.moviesList[0]
    private val id = movie.id

    private val favoriteLiveData = MutableLiveData<Movie>()
    private val isFavoriteLiveData = MutableLiveData<Boolean>()
    private val favoritesRepositoryMock: FavoritesRepository = mock {
        on { getFavoriteById(id) } doReturn favoriteLiveData
        on { isFavorite(id) } doReturn isFavoriteLiveData
    }


    @Before
    fun setup() {
        isFavoriteLiveData.value = true
        viewModel = FavoriteDetailsViewModel(id, favoritesRepositoryMock)
    }

    @Test
    fun `when viewModel inits, it should getFavoriteById and expose it`() {
        //WHEN
        favoriteLiveData.value = movie

        //THEN
        verify(favoritesRepositoryMock).getFavoriteById(id)

        //AND
        val fetchedFavorite = viewModel.movie
            .test()
            .awaitValue()
            .value()
       assertEquals(movie, fetchedFavorite)
    }

    @Test
    fun `when movie is marked as not favorite, last loaded movie is still provided`() {
        //GIVEN
        // Initially, there is a favorite movie
        favoriteLiveData.value = movie
        var providedMovie = viewModel.movie
            .test()
            .awaitValue()
            .value()
        assertEquals(movie, providedMovie)

        //WHEN
        // Movie is marked as not favorite
        isFavoriteLiveData.value = false
        favoriteLiveData.value = null

        //THEN
        providedMovie = viewModel.movie
            .test()
            .awaitValue()
            .value()
        assertEquals(movie, providedMovie)
    }
}