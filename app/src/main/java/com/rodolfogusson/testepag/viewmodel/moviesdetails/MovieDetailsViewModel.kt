package com.rodolfogusson.testepag.viewmodel.moviesdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Movie

class MovieDetailsViewModel(
    id: Int,
    moviesRepository: MoviesRepository
) : ViewModel() {

    val movie: LiveData<Movie> = moviesRepository.getMovieById(id)

}
