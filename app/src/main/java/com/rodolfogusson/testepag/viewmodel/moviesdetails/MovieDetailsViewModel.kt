package com.rodolfogusson.testepag.viewmodel.moviesdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.infrastructure.data.repository.GenresRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Movie

class MovieDetailsViewModel(
    val id: Int,
    val moviesRepository: MoviesRepository,
    val genresRepository: GenresRepository
) : ViewModel() {

    val movie: LiveData<Movie> = moviesRepository.getMovieBy(id)

}
