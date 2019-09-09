package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.*
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

@ExperimentalCoroutinesApi
class MoviesListViewModelTest {
    private lateinit var viewModel: MoviesListViewModel
    private val moviesRepositoryMock = mock<MoviesRepository>()

    private val moviesReturn = listOf(
        Movie(
            1,
            "Filme 1",
            LocalDate.parse("2019-09-05"),
            "/wF6SNPcUrTKFA4fOFfukm7zQ3ob.jpg",
            7.4,
            10,
            "alsfiae alisejflaiejflaisejlf iajeli"
        ),
        Movie(
            2,
            "Filme 2",
            LocalDate.parse("2019-09-05"),
            "/kRLIHHf4KrwYFInU08akbzSGCrW.jpg",
            8.1,
            1,
            "afaswefasiejçlfasiejfli slei lsiejlsijlsie"
        )
    )

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = MoviesListViewModel(moviesRepositoryMock)
    }

    @Test
    fun `when viewModel initiates, it should getMovies`() = runBlockingTest {
        //WHEN
        // viewModel is instantiated

        //THEN
        verify(moviesRepositoryMock).getMovies(any())
    }

    @Test
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
    }
}