package com.rodolfogusson.testepag.viewmodel.details.moviedetails

import androidx.lifecycle.LiveData
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.viewmodel.details.DetailsViewModel

class MovieDetailsViewModel(
    id: Int,
    moviesRepository: MoviesRepository,
    favoritesRepository: FavoritesRepository
) : DetailsViewModel(id, favoritesRepository) {

    override val movie: LiveData<Movie> = moviesRepository.getMovieById(id)
}
