package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.infrastructure.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Movie

class MoviesListViewModel(val moviesRepository: MoviesRepository) : ViewModel() {
    var movies: LiveData<List<Movie>>

    init {
        movies = moviesRepository.getMovies()
    }
}