package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.threeten.bp.LocalDate
import java.lang.Exception

class MoviesListViewModelTest {
    private lateinit var viewModel: MoviesListViewModel
    private val moviesRepositoryMock = mock<MoviesRepository>()
    private val genresRepositoryMock = mock<GenresRepository>()

    private val moviesReturn = listOf(
        Movie(
            1,
            "Filme 1",
            LocalDate.parse("2019-09-05"),
            "/wF6SNPcUrTKFA4fOFfukm7zQ3ob.jpg",
            7.4,
            10,
            "alsfiae alisejflaiejflaisejlf iajeli",
            listOf()
        ),
        Movie(
            2,
            "Filme 2",
            LocalDate.parse("2019-09-05"),
            "/kRLIHHf4KrwYFInU08akbzSGCrW.jpg",
            8.1,
            1,
            "afaswefasiejçlfasiejfli slei lsiejlsijlsie",
            listOf()
        )
    )

    private val genresReturn = listOf(
        Genre(1, "AÇÃO"),
        Genre(2, "AVENTURA"),
        Genre(3, "DRAMA")
    )

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = MoviesListViewModel(moviesRepositoryMock, genresRepositoryMock)
    }

    @Test
    fun `when viewModel inits, it should getGenres`() {
        //WHEN
        // viewModel is instantiated

        //THEN
        verify(genresRepositoryMock).getGenres()
    }

    @Test
    fun `when viewModel inits, it should getMovies`() {
        //GIVEN
        whenever(genresRepositoryMock.getGenres())
            .thenReturn(MutableLiveData<List<Genre>>().apply { value = genresReturn })
        whenever(moviesRepositoryMock.getMovies(genresReturn))
            .thenReturn(MutableLiveData<List<Movie>>().apply { value = moviesReturn })

        //WHEN
        viewModel = MoviesListViewModel(moviesRepositoryMock, genresRepositoryMock)

        //THEN
        viewModel.movies
            .test()
            .awaitValue()

        verify(moviesRepositoryMock).getMovies(any())
    }

    @Test
    fun `after init, genres and movies should contain correct data`() {
        //GIVEN
        whenever(genresRepositoryMock.getGenres())
            .thenReturn(MutableLiveData<List<Genre>>().apply { value = genresReturn })
        whenever(moviesRepositoryMock.getMovies(genresReturn))
            .thenReturn(MutableLiveData<List<Movie>>().apply { value = moviesReturn })

        //WHEN
        viewModel = MoviesListViewModel(moviesRepositoryMock, genresRepositoryMock)

        //THEN
        viewModel.genres
            .test()
            .assertHasValue()
            .assertValue(genresReturn)

        viewModel.movies
            .test()
            .assertHasValue()
            .assertValue(moviesReturn)
    }

    @Test//(expected = Throwable::class)
    fun `if getMovies fails, error should be emitted`() {
        //GIVEN
        whenever(genresRepositoryMock.getGenres())
            .thenReturn(MutableLiveData<List<Genre>>().apply { value = genresReturn })
        whenever(moviesRepositoryMock.getMovies(genresReturn)).doThrow(Exception())

        //WHEN
        var exceptionOccured = false
        try {
            viewModel = MoviesListViewModel(moviesRepositoryMock, genresRepositoryMock)
            viewModel.error
                .test()
                .awaitValue()
        } catch (e: Exception) {
            exceptionOccured = true
        }
        assert(exceptionOccured)

        //THEN
        viewModel.error
            .test()
            .assertValue(false)
    }

    /*@Test
    fun `after getting movies, viewModel should expose them`() {
        //GIVEN
        whenever(moviesRepositoryMock.getMovies(any())).thenReturn(moviesReturn)

        //WHEN
        viewModel = MoviesListViewModel(moviesRepositoryMock)

        //THEN
        viewModel.movies.observeForever {
            assertEquals(moviesReturn, it)
        }
    }

    @Test
    fun `when getMovies fails, error is emitted`() = runBlockingTest {
        //GIVEN
        whenever(moviesRepositoryMock.getMovies(any())).doThrow(Exception())

        //WHEN
        viewModel = MoviesListViewModel(moviesRepositoryMock)

        //THEN
        viewModel.error.observeForever {
            assertEquals(true, it)
        }
    }*/
}