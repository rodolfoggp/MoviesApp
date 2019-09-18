package com.rodolfogusson.testepag.viewmodel.moviesdetails

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class MovieDetailsViewModelTest {

    private lateinit var viewModel: MovieDetailsViewModel

    private val moviesRepositoryMock: MoviesRepository = mock()
    private val genresRepositoryMock: GenresRepository = mock()
    private val id = 3

    @Before
    fun setup() {
        viewModel = MovieDetailsViewModel(id, moviesRepositoryMock, genresRepositoryMock)
    }

    @Test
    fun `when viewModel inits, it should getMovieBy id`() {
        //WHEN
        //viewModel inits

        //THEN
        verify(moviesRepositoryMock).getMovieBy(id)
    }
}