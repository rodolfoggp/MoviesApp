package com.rodolfogusson.testepag.viewmodel.moviesdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.infrastructure.data.repository.MoviesRepository
import com.rodolfogusson.testepag.model.Movie

class MovieDetailsViewModel(
    val id: Int,
    moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    val movie: LiveData<Movie> = moviesRepository.getMovieById(id)
    val isFavorite: LiveData<Boolean> = favoritesRepository.isFavorite(id)

    fun onFavoriteButtonClicked() {
        isFavorite.value?.let { isFavorite ->
            if (isFavorite) {
                favoritesRepository.remove(id)
            } else {
                favoritesRepository.add(id)
            }
        }
    }
}
