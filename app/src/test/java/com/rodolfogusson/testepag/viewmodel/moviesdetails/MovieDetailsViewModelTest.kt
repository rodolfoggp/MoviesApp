package com.rodolfogusson.testepag.viewmodel.moviesdetails

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import org.junit.Before

import org.junit.Test

class MovieDetailsViewModelTest {

    private lateinit var viewModel: MovieDetailsViewModel

    private val moviesRepositoryMock: MoviesRepository = mock()
    private val id = 3

    @Before
    fun setup() {
        viewModel = MovieDetailsViewModel(id, moviesRepositoryMock)
    }

    @Test
    fun `when viewModel inits, it should getMovieById`() {
        //WHEN
        //viewModel inits

        //THEN
        verify(moviesRepositoryMock).getMovieById(id)
    }
}