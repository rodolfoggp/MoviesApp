package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rodolfogusson.testepag.infrastructure.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate

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
    fun `when viewModel initiates, it should getMovies`() {
        //WHEN
        // viewModel is instantiated

        //THEN
        verify(moviesRepositoryMock).getMovies()
    }

    @Test
    fun `after getting movies, viewModel should expose them`() {
        //GIVEN
        val data = MutableLiveData<List<Movie>>()
        data.value = moviesReturn
        whenever(moviesRepositoryMock.getMovies()).thenReturn(data)

        //WHEN
        viewModel = MoviesListViewModel(moviesRepositoryMock)

        //THEN
        viewModel.movies.observeForever {
            assertEquals(moviesReturn, it)
        }
    }
}