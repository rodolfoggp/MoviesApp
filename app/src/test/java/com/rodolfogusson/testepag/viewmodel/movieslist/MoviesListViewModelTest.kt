package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyList
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

    private fun configureMoviesList() {
        for (movie in moviesList) movie.genres = listOf()
    }

    private val genresList = listOf(
        Genre(1, "AÇÃO"),
        Genre(2, "AVENTURA"),
        Genre(3, "DRAMA")
    )

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        configureMoviesList()

        //Default repositories' return values:
        genresReturn.value = Resource.success(genresList)
        moviesReturn.value = Resource.success(moviesList)

        //Repositories' returns config:
        whenever(genresRepositoryMock.getGenres())
            .thenReturn(genresReturn)

        whenever(moviesRepositoryMock.getMovies(genresList, 1))
            .thenReturn(moviesReturn)

        //ViewModel instantiation
        viewModel = MoviesListViewModel(moviesRepositoryMock, genresRepositoryMock)
    }

    @Test
    fun `when viewModel inits, it should getGenres`() {
        //WHEN
        //favoritesViewModel inits

        //THEN
        viewModel.genres
            .test()
            .awaitValue()

        verify(genresRepositoryMock).getGenres()
    }

    @Test
    fun `when viewModel inits, it should getMovies`() {
        //WHEN
        //favoritesViewModel inits

        //THEN
        viewModel.movies
            .test()
            .awaitValue()

        verify(moviesRepositoryMock).getMovies(any(), any())
    }

    @Test
    fun `after init, genres and movies should contain correct data`() {
        //WHEN
        //favoritesViewModel inits

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
        //favoritesViewModel getMovies

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
        //favoritesViewModel getGenres

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

    @Test
    fun `when getting genres and movies, progress should be visible`() {
        //WHEN
        //favoritesViewModel getGenres

        //THEN
        var value = viewModel.isProgressVisible
            .test()
            .value()
        assertEquals(true, value)

        //AND WHEN
        viewModel.movies
            .test()
            .awaitValue()

        //THEN
        value = viewModel.isProgressVisible
            .test()
            .value()
        assertEquals(true, value)
    }

    @Test
    fun `onMoviesLoaded, loading should end`() {
        //WHEN
        viewModel.onMoviesLoaded(Status.SUCCESS)

        //THEN
        val progressVisible = viewModel.isProgressVisible
            .test()
            .value()

        val nextPageProgressVisible = viewModel.isNextPageProgressVisible
            .test()
            .value()

        assertEquals(false, progressVisible)
        assertEquals(false, nextPageProgressVisible)
    }

    @Test
    fun `viewModel loads pages incrementally`() {
        //GIVEN
        whenever(moviesRepositoryMock.getTotalPages).thenReturn(16)
        viewModel.movies
            .test()
            .awaitValue()

        verify(moviesRepositoryMock).getMovies(genresList, 1)
        viewModel.onMoviesLoaded(Status.SUCCESS)

        //WHEN
        viewModel.loadMoreMovies()
        viewModel.movies
            .test()
            .awaitValue()

        //THEN
        verify(moviesRepositoryMock).getMovies(genresList, 2)
    }

    @Test
    fun `when already loading, it should not loadMoreMovies`() {
        //GIVEN
        whenever(moviesRepositoryMock.getTotalPages).thenReturn(16)
        viewModel.movies
            .test()
            .awaitValue()

        //WHEN
        viewModel.loadMoreMovies()
        viewModel.movies
            .test()
            .awaitValue()
            .assertHistorySize(1)

        //THEN
        verify(moviesRepositoryMock, times(1)).getMovies(anyList(), any())
    }

    @Test
    fun `when getGenres fails, retryGetMovies should getGenres too`() {
        //GIVEN
        genresReturn.value = Resource.error(Throwable())

        val hasError = viewModel.genres
            .test()
            .awaitValue()
            .value()
            .hasError
        assertEquals(true, hasError)

        //WHEN
        viewModel.retryGetMovies()

        //THEN
        verify(genresRepositoryMock, times(2)).getGenres()
    }

    @Test
    fun `when getMovies fails, retryGetMovies should getMovies only once, until loading ends`() {
        //GIVEN
        moviesReturn.value = Resource.error(Throwable())

        val hasError = viewModel.movies
            .test()
            .awaitValue()
            .value()
            .hasError
        assertEquals(true, hasError)

        //WHEN
        viewModel.onMoviesLoaded(Status.ERROR)
        viewModel.retryGetMovies()
        //Retry again before the first retry ends
        viewModel.retryGetMovies()

        //THEN
        //Verify the first retry calls getMovies but the second retry is useless
        verify(moviesRepositoryMock, times(2)).getMovies(anyList(), anyInt())
    }

    @Test
    fun `at the start, movies will be unsorted`() {
        //GIVEN
        //favoritesViewModel inits

        //WHEN
        val movies = viewModel.movies
            .test()
            .awaitValue()
            .value()
            .data

        assertEquals(moviesList, movies)
    }

    @Test
    fun `when sorting order changes, movies are sorted accordingly`() {
        //GIVEN
        //favoritesViewModel inits

        //WHEN
        val ascendingIndex = viewModel.orderArray.indexOf(SortingOrder.ASCENDING)
        viewModel.onSortingSelected(ascendingIndex)

        //THEN
        var movies = viewModel.movies
            .test()
            .awaitValue()
            .value()
            .data
        val ascendingMoviesList = moviesList.sortedBy { movie -> movie.releaseDate }
        assertEquals(ascendingMoviesList, movies)

        //AND WHEN
        val descendingIndex = viewModel.orderArray.indexOf(SortingOrder.DESCENDING)
        viewModel.onSortingSelected(descendingIndex)

        //THEN
        movies = viewModel.movies
            .test()
            .awaitValue()
            .value()
            .data
        val descendingMoviesList = moviesList.sortedByDescending { movie -> movie.releaseDate }
        assertEquals(descendingMoviesList, movies)
    }
}