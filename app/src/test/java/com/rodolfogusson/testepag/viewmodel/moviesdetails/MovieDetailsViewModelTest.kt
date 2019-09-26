package com.rodolfogusson.testepag.viewmodel.moviesdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate

class MovieDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieDetailsViewModel

    private val id = 3
    private val movie = Movie(
        1,
        "Filme 1",
        "Descrição 1",
        LocalDate.parse("2019-05-12"),
        "/wF6SNPcUrTKFA4fOFfukm7zQ3ob.jpg",
        6.4,
        10
    )

    private val movieLiveData = MutableLiveData<Movie>()
    private val isFavoriteLiveData = MutableLiveData<Boolean>()

    private val moviesRepositoryMock: MoviesRepository = mock {
        on { getMovieById(id) } doReturn movieLiveData
    }
    private val favoritesRepositoryMock: FavoritesRepository = mock {
        on { isFavorite(id) } doReturn isFavoriteLiveData
    }

    @Before
    fun setup() {
        isFavoriteLiveData.value = false
        movieLiveData.value = movie
        viewModel = MovieDetailsViewModel(id, moviesRepositoryMock, favoritesRepositoryMock)
    }

    @Test
    fun `when viewModel inits, it should getMovieById`() {
        //WHEN
        //vm inits

        //THEN
        verify(moviesRepositoryMock).getMovieById(id)
    }

    @Test
    fun `when viewModel inits, it should get isFavorite`() {
        //WHEN
        //vm inits

        //THEN
        verify(favoritesRepositoryMock).isFavorite(id)
    }

    @Test
    fun `onFavoriteButtonClicked, if movie is not favorite, it should be added to favorites list`() {
        //GIVEN
        //vm inits
        var isFavorite = viewModel.isFavorite
            .test()
            .value()
        assertEquals(false, isFavorite)

        //WHEN
        viewModel.onFavoriteButtonClicked()

        //THEN
        verify(favoritesRepositoryMock).add(movie)
        isFavoriteLiveData.value = true
        isFavorite = viewModel.isFavorite
            .test()
            .awaitValue()
            .value()
        assertEquals(true, isFavorite)
    }

    @Test
    fun `onFavoriteButtonClicked, if movie isFavorite, it should be removed from favorites list`() {
        //GIVEN
        //vm inits
        isFavoriteLiveData.value = true
        var isFavorite = viewModel.isFavorite
            .test()
            .value()
        assertEquals(true, isFavorite)

        //WHEN
        viewModel.onFavoriteButtonClicked()

        //THEN
        verify(favoritesRepositoryMock).remove(movie)
        isFavoriteLiveData.value = false
        isFavorite = viewModel.isFavorite
            .test()
            .awaitValue()
            .value()
        assertEquals(false, isFavorite)
    }
}