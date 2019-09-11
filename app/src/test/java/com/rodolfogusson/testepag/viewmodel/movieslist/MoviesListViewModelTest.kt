package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rodolfogusson.testepag.infrastructure.data.Resource
import com.rodolfogusson.testepag.infrastructure.data.Status
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate

class MoviesListViewModelTest {
    private lateinit var viewModel: MoviesListViewModel

    private val moviesRepositoryMock = mock<MoviesRepository>()
    private val genresRepositoryMock = mock<GenresRepository>()

    private val moviesReturn = MutableLiveData<Resource<List<Movie>>>()
    private val genresReturn = MutableLiveData<Resource<List<Genre>>>()

    private val moviesList = listOf(
        Movie(
            1,
            "Filme 1",
            "Descrição 1",
            LocalDate.parse("2019-08-26"),
            "/wF6SNPcUrTKFA4fOFfukm7zQ3ob.jpg",
            6.4,
            10,
            listOf()
        ),
        Movie(
            1,
            "Filme 2",
            "Descrição 2",
            LocalDate.parse("2019-05-12"),
            "/foEOVg4HQl2VzKzTh27CAHmXyg.jpg",
            7.9,
            17,
            listOf()
        )
    )

    private val genresList = listOf(
        Genre(1, "AÇÃO"),
        Genre(2, "AVENTURA"),
        Genre(3, "DRAMA")
    )

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        //Default repository return values:
        genresReturn.value = Resource.success(genresList)
        moviesReturn.value = Resource.success(moviesList)

        //Repositories' returns config:
        whenever(genresRepositoryMock.getGenres())
            .thenReturn(genresReturn)

        whenever(moviesRepositoryMock.getMovies(genresList))
            .thenReturn(moviesReturn)

        //ViewModel instantiation
        viewModel = MoviesListViewModel(moviesRepositoryMock, genresRepositoryMock)
    }

    @Test
    fun `when viewModel inits, it should getGenres`() {
        //WHEN
        //viewModel inits

        //THEN
        verify(genresRepositoryMock).getGenres()
    }

    @Test
    fun `when viewModel inits, it should getMovies`() {
        //WHEN
        //viewModel inits

        //THEN
        viewModel.movies
            .test()
            .awaitValue()

        verify(moviesRepositoryMock).getMovies(any())
    }

    @Test
    fun `after init, genres and movies should contain correct data`() {
        //WHEN
        //viewModel inits

        //THEN
        val genresResource = viewModel.genres
            .test()
            .value()
        assertEquals(genresList, genresResource.data)

        val moviesResource = viewModel.movies
            .test()
            .value()
        assertEquals(moviesList, moviesResource.data)
    }

    @Test
    fun `if getMovies fails, error should be emitted in movies`() {
        //GIVEN
        //getMovies fails
        moviesReturn.value = Resource.error(Throwable())

        //WHEN
        //viewModel getMovies

        //THEN
        val moviesResource = viewModel.movies
            .test()
            .value()
        assertEquals(Status.ERROR, moviesResource.status)
    }

    @Test
    fun `if getGenres fails, errors should be emitted in movies and genres`() {
        //GIVEN
        //getGenres fails
        genresReturn.value = Resource.error(Throwable())

        //WHEN
        //viewModel getGenres

        //THEN
        val genresResource = viewModel.genres
            .test()
            .value()
        assertEquals(Status.ERROR, genresResource.status)

        val moviesResource = viewModel.movies
            .test()
            .value()
        assertEquals(Status.ERROR, moviesResource.status)
    }
}